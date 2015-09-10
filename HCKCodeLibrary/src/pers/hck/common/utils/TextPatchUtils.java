package pers.hck.common.utils;

import java.util.LinkedList;

import pers.hck.lib.diff_match_patch;
import pers.hck.lib.diff_match_patch.Diff;
import pers.hck.lib.diff_match_patch.Operation;
import pers.hck.lib.diff_match_patch.Patch;

public class TextPatchUtils {
	private final static diff_match_patch dmp = new diff_match_patch();
	
	public static LinkedList<Diff> getDiffs(String originalText, String changedText){
		LinkedList<Diff> diffs = dmp.diff_main(originalText, changedText);
		return diffs;
	}
	
	public static LinkedList<Patch> makePatch(LinkedList<Diff> diffs){
		LinkedList<Patch> patches = dmp.patch_make(diffs);
		return patches;
	}
	
	public static LinkedList<Diff> getDisDiffs(LinkedList<Diff> diffs){
		LinkedList<Diff> disDiffs = copyDiffs(diffs);
		for (Diff disDiff:disDiffs){
			if (disDiff.operation.equals(Operation.DELETE)){
				disDiff.operation = Operation.INSERT;
			}else if (disDiff.operation.equals(Operation.INSERT)){
				disDiff.operation = Operation.DELETE;
			}
		}
		return disDiffs;
	}
	
	public static LinkedList<Patch> getPatches(String originalText, String changedText){
		LinkedList<Patch> patches = dmp.patch_make(originalText, changedText);
		return patches;
	}
	
	public static String applyPatches(LinkedList<Patch> patches, String originalText){
		Object[] results = dmp.patch_apply(patches, originalText);
		try{
			String newText = String.valueOf(results[0]);
			return newText;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static LinkedList<Patch> getDisPatchesByDiffs(LinkedList<Diff> diffs){
		LinkedList<Patch> disPatches = dmp.patch_make(diffs);
		return disPatches;
	}
	
	public static LinkedList<Patch> getDisPatches(LinkedList<Patch> patches){
		LinkedList<Patch> disPatches = copyPatches(patches);
		for (Patch disPatch:disPatches){
			for (Diff diff:disPatch.diffs){
				if (diff.operation.equals(Operation.DELETE)){
					diff.operation = Operation.INSERT;
				}else if (diff.operation.equals(Operation.INSERT)){
					diff.operation = Operation.DELETE;
				}
			}
		}
		return disPatches;
	}
	
	public static void displayPatches(LinkedList<Patch> patches){
		LinkedList<Patch> tempPatches = copyPatches(patches);
		for (Patch tempPatch:tempPatches){
			for (Diff diff:tempPatch.diffs){
				System.out.println("Text: "+diff.text+" | Operation: "+diff.operation);
			}
		}
	}
	
	public static void displayDiffs(LinkedList<Diff> diffs){
		LinkedList<Diff> tempDiffs = new LinkedList<Diff>(diffs) ;
		for (Diff tempDiff:tempDiffs){
			System.out.println("Text: "+tempDiff.text+" | Operation: "+tempDiff.operation);
		}
	}
	
	public static LinkedList<Patch> copyPatches(LinkedList<Patch> patches){
		LinkedList<Patch> newPatches = new LinkedList<Patch>();
		for (Patch patch:patches){
			Patch newPatch = new Patch();
			newPatch.diffs = copyDiffs(patch.diffs);
			newPatches.add(patch);
		}
		return newPatches;
	}
	
	public static LinkedList<Diff> copyDiffs(LinkedList<Diff> diffs){
		LinkedList<Diff> newDiffs = new LinkedList<Diff>();
		for (Diff diff:diffs){
			Diff newDiff = new Diff(diff.operation, diff.text);
			newDiffs.add(newDiff);
		}
		return newDiffs;
	}
}

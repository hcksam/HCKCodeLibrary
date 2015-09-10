package pers.hck.common.utils;

import java.util.LinkedList;

import pers.hck.lib.diff_match_patch;
import pers.hck.lib.diff_match_patch.Diff;
import pers.hck.lib.diff_match_patch.Operation;
import pers.hck.lib.diff_match_patch.Patch;

public class TextPatchUtils {
	public final static diff_match_patch dmp = new diff_match_patch();
	
	public static LinkedList<Diff> makeDiffs(String originalText, String changedText){
		return dmp.diff_main(originalText, changedText);
	}
	
	public static LinkedList<Patch> makePatches(LinkedList<Diff> diffs){
		return dmp.patch_make(diffs);
	}
	
	public static LinkedList<Patch> makePatches(String originalText, String changedText){
		return dmp.patch_make(originalText, changedText);
	}
	
	public static LinkedList<Diff> makeDisDiffs(LinkedList<Diff> diffs){
		LinkedList<Diff> disDiffs = new LinkedList<Diff>();
		for (Diff diff:diffs){
			if (diff.operation.equals(Operation.DELETE)){
				Diff disDiff = new Diff(Operation.INSERT, diff.text);
				disDiffs.add(disDiff);
			}else if (diff.operation.equals(Operation.INSERT)){
				Diff disDiff = new Diff(Operation.DELETE, diff.text);
				disDiffs.add(disDiff);
			}else{
				disDiffs.add(diff);
			}
		}
		return disDiffs;
	}
	
	public static LinkedList<Patch> makeDisPatches(LinkedList<Patch> patches){
		LinkedList<Patch> disPatches = new LinkedList<Patch>();
		for (Patch patch:patches){
			LinkedList<Diff> diffs = makeDisDiffs(patch.diffs);
			LinkedList<Patch> tempPatches = dmp.patch_make(diffs);
			for (Patch tempPatch:tempPatches){
				disPatches.add(tempPatch);
			}
		}
		return disPatches;
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
	
	public static String restorePatches(LinkedList<Patch> patches, String changedText){
		LinkedList<Patch> disPatches = makeDisPatches(patches);
		return applyPatches(disPatches, changedText);
	}
	
	public static void displayPatches(LinkedList<Patch> patches){
		for (Patch patch:patches){
			for (Diff diff:patch.diffs){
				System.out.println("Text: "+diff.text+" | Operation: "+diff.operation);
			}
		}
	}
	
	public static void displayDiffs(LinkedList<Diff> diffs){
		for (Diff diff:diffs){
			System.out.println("Text: "+diff.text+" | Operation: "+diff.operation);
		}
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
	
	public static void main(String[] args){
		String s1 = "CF Y已編輯 1 個項目";
		String s2 = "CF Y已重新命名 1 個項目";
		String cs = "測試";
//		for (int i=0;i<100;i++){
//			s1 = cs + s1;
//			s2 = cs + s2;
//		}
//		for (int i=0;i<100;i++){
//			s1 += cs;
//			s2 += cs;
//		}
		
		LinkedList<Diff> diffs = TextPatchUtils.makeDiffs(s1, s2);
		
		LinkedList<Patch> patches = TextPatchUtils.makePatches(s1, s2);
		LinkedList<Patch> patches2 = TextPatchUtils.getPatches(s2, s1);
//		LinkedList<Patch> disPatches = TextPatchUtils.makePatches(TextPatchUtils.makeDisDiffs(diffs));
		LinkedList<Patch> disPatches = TextPatchUtils.makeDisPatches(patches);
		
//		LinkedList<Diff> disDiffs = TextPatchUtils.getDisDiffs(diffs);
//		LinkedList<Patch> disPatches2 = TextPatchUtils.getDisPatchesByDiffs(diffs);
		
		System.out.println("patches");
		TextPatchUtils.displayPatches(patches);
//		TextPatchUtils.displayDiffs(diffs);
		System.out.println("patches2");
		TextPatchUtils.displayPatches(patches2);
		System.out.println("disPatches");
		TextPatchUtils.displayPatches(disPatches);
//		TextPatchUtils.displayDiffs(disDiffs);
		String newText = TextPatchUtils.applyPatches(disPatches, s2);
		System.out.println("newText: "+newText);
	}
}

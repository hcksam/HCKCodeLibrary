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
	
	public static int getChangedFirstPosition(LinkedList<Patch> patches, String text){
		for (Patch patch:patches){
			for (Diff diff:patch.diffs){
				int index = dmp.match_main(text, diff.text, patch.start2);
				if (diff.operation.equals(Operation.EQUAL)){
					index =  index + diff.text.length();
				}
				if (index < 0){
					return 0;
				}
				if (index >= text.length()){
					return text.length()-1;
				}
				return index;
			}
		}
		return 0;
	}
	
	public static int getChangedLastPosition(LinkedList<Patch> patches, String text){
		for (Patch patch:patches){
			for (int i=patch.diffs.size()-1;i>=0;){
				Diff diff = patch.diffs.get(i);
				int index = dmp.match_main(text, diff.text, patch.start2);
				if (diff.operation.equals(Operation.EQUAL)){
					index = index-1;
				}else if (diff.operation.equals(Operation.DELETE)){
					index = index + diff.text.length();
				}else if (diff.operation.equals(Operation.INSERT)){
					index = index + diff.text.length();
				}
				if (index < 0){
					return 0;
				}
				if (index >= text.length()){
					return text.length()-1;
				}
				return index;
			}
		}
		return 0;
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
}

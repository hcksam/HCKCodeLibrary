import java.util.LinkedList;

import pers.hck.common.utils.TextPatchUtils;
import pers.hck.lib.diff_match_patch.Patch;


public class TestTextPatchUtils {

	public static void main(String[] args) {
		String s1 = "CF Y已編輯 1 個項目";
		String s2 = "CF Y已重新命名 1 個項目";
//		String s1 = "編輯 1 個項目";
//		String s2 = "重新命名 1 個項目";
//		String s1 = "CF Y已編輯";
//		String s2 = "CF Y已重新命名";
//		String s1 = " Y已編輯 1 個項目";
//		String s2 = "CF Y已重新命名 1 個項目";
		String cs = "測試";
//		for (int i=0;i<100;i++){
//			s1 = cs + s1;
//			s2 = cs + s2;
//		}
//		for (int i=0;i<100;i++){
//			s1 += cs;
//			s2 += cs;
//		}
		
//		LinkedList<Diff> diffs = TextPatchUtils.makeDiffs(s1, s2);
		
		LinkedList<Patch> patches = TextPatchUtils.makePatches(s1, s2);
//		LinkedList<Patch> patches2 = TextPatchUtils.getPatches(s2, s1);
//		LinkedList<Patch> disPatches = TextPatchUtils.makePatches(TextPatchUtils.makeDisDiffs(diffs));
//		LinkedList<Patch> disPatches = TextPatchUtils.makeDisPatches(patches);
		
//		LinkedList<Diff> disDiffs = TextPatchUtils.getDisDiffs(diffs);
//		LinkedList<Patch> disPatches2 = TextPatchUtils.getDisPatchesByDiffs(diffs);
		
//		System.out.println("patches");
//		TextPatchUtils.displayPatches(patches);
//		TextPatchUtils.displayDiffs(diffs);
//		System.out.println("patches2");
//		TextPatchUtils.displayPatches(patches2);
//		System.out.println("disPatches");
//		TextPatchUtils.displayPatches(disPatches);
//		TextPatchUtils.displayDiffs(disDiffs);
		String newText = TextPatchUtils.applyPatches(patches, s1);
		System.out.println("newText: "+newText);
		String originalText = TextPatchUtils.restorePatches(patches, newText);
		System.out.println("originalText: "+originalText);
		int firstChangedPosition = TextPatchUtils.getChangedFirstPosition(patches, originalText);
		int lastChangedPosition = TextPatchUtils.getChangedLastPosition(patches, newText);
//		String firstChangedText = s1.substring(firstChangedPosition);
//		System.out.println("firstChangedText: "+originalText.charAt(firstChangedPosition));
		System.out.println("lastChangedPosition: "+newText.charAt(lastChangedPosition));
	}

}

package com.loy.e.common.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class TreeUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  List<TreeNode> build(List<? extends Tree> list){
		
		if (list != null) {
			int len = list.size();
			if (len > 0) {
				List<TreeNode> mainList = new ArrayList<TreeNode>();
				Map<String, TreeNode> map = new HashMap<String, TreeNode>();
				for (Tree t : list) {
					String id = t.getId();
					String parentId = t.getParentId();
					Object data = t.getData();
					TreeNode treeNode = new TreeNode();
					treeNode.setData(data);
					treeNode.setId(id);
					treeNode.setParentId(parentId);
					if (parentId == null ||"".equals(parentId)) {
						mainList.add(treeNode);
					}
					map.put(id, treeNode);
				}
				Collection<TreeNode> values = map.values();
				for (TreeNode t : values) {
					String parentId = t.getParentId();
					if (parentId != null && !"".equals(parentId)) {
						TreeNode parent = map.get(parentId);
						List<TreeNode> children = parent.getChildren();
						if (children == null) {
							children = new ArrayList<TreeNode>();
							parent.setChildren(children);
						}
						children.add(t);
					}
				}
				return mainList;
			}
		}
		return null;
	}
}
package com.example.api_project.vo.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeBuild {

        // 保存参与构建树形的所有数据（通常数据库查询结果）
        public List<TreeNode> nodeList = new ArrayList<>();

        /**
         *  构造方法
         *  @param nodeList 将数据集合赋值给nodeList，即所有数据作为所有节点。
         */
        public TreeBuild(List<TreeNode> nodeList){
            this.nodeList = nodeList;
        }

        /**
         *   获取需构建的所有根节点（顶级节点） "0"
         *   @return 所有根节点List集合
         */
        public List<TreeNode> getRootNode(){
            // 保存所有根节点（所有根节点的数据）
            List<TreeNode> rootNodeList = new ArrayList<>();
            // treeNode：查询出的每一条数据（节点）
            for (TreeNode treeNode : nodeList){
                // 判断当前节点是否为根节点，此处注意：若parentId类型是String，则要采用equals()方法判断。
                if ("" == treeNode.getParentCategoryKey() || null==treeNode.getParentCategoryKey()) {
                    // 是，添加
                    rootNodeList.add(treeNode);
                }
            }
            return rootNodeList;
        }

        /**
         *  根据每一个顶级节点（根节点）进行构建树形结构
         *  @return  构建整棵树
         */
        public List<TreeNode> buildTree(){
            // treeNodes：保存一个顶级节点所构建出来的完整树形
            List<TreeNode> treeNodes = new ArrayList<TreeNode>();
            // getRootNode()：获取所有的根节点
            for (TreeNode treeRootNode : getRootNode()) {
                // 将顶级节点进行构建子树
                treeRootNode = buildChildTree(treeRootNode);
                // 完成一个顶级节点所构建的树形，增加进来
                treeNodes.add(treeRootNode);
            }
            return treeNodes;
        }

        /**
         *  递归-----构建子树形结构
         *  @param  pNode 根节点（顶级节点）
         *  @return 整棵树
         */
        public TreeNode buildChildTree(TreeNode pNode){
            List<TreeNode> childTree = new ArrayList<TreeNode>();
            // nodeList：所有节点集合（所有数据）
            for (TreeNode treeNode : nodeList) {
                // 判断当前节点的父节点ID是否等于根节点的ID，即当前节点为其下的子节点
                if(treeNode.getParentCategoryKey()!=null){
                    if (treeNode.getParentCategoryKey().equals(pNode.getCategoryKey())) {
                        // 再递归进行判断当前节点的情况，调用自身方法
                        childTree.add(buildChildTree(treeNode));
                    }
                }
            }
            // for循环结束，即节点下没有任何节点，树形构建结束，设置树结果
            pNode.setChildren(childTree);
            return pNode;
        }

    }
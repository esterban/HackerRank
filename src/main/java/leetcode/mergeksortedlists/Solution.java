package leetcode.mergeksortedlists;

import java.util.Arrays;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public class Solution {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode resultHead = null;

        ListNode[] headList = copyHeads(lists);
        boolean okay = Arrays.stream(headList).anyMatch(e -> e != null);

//        AbstractMap.SimpleEntry

        return resultHead;
    }

    private ListNode[] copyHeads(ListNode[] nodeList) {
        ListNode[] copy = Arrays.copyOf(nodeList, nodeList.length);
        return copy;
    }


    private ListNode findNextNode(ListNode[] headArray) {
        int minValue = Integer.MAX_VALUE;
        ListNode nextNode = null;

        for (ListNode node : headArray) {
            if (node.val < minValue) {
                nextNode = node;
                minValue = node.val;
            }
        }

        return nextNode;
    }
}
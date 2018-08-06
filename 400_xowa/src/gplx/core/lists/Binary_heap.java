/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.lists; import gplx.*; import gplx.core.*;
public class Binary_heap {
	private final    ComparerAble comparer;
	private boolean is_max;
	private Object[] heap;
	private int size;
	public Binary_heap(ComparerAble comparer, boolean is_max, int capacity) {
		this.comparer = comparer;
		this.is_max = is_max;
		this.heap = new Object[capacity];
		this.size = 0;
	}
	public Object Get() {
		if (size == 0) throw Err_.new_wo_type("heap is empty");
		return heap[0];
	}
	public void Add(Object val) {
		if (size == heap.length) {
			int old_max = heap.length;
			Object[] new_heap = new Object[old_max * 2];
			for (int i = 0; i < old_max; i++)
				new_heap[i] = heap[i];
			heap = new_heap;
		}
		heap[size++] = val;
		Heapify_up(size - 1);
	}
	public Object Pop() {return Pop(0);}
	public Object Pop(int idx) {
		if (size == 0) throw Err_.new_wo_type("heap is empty");
		Object val = heap[idx];
		heap[idx] = heap[size -1];
		size--;
		Heapify_down(idx);
		return val;
	}

	private int Parent(int idx) {
		return (idx - 1) / SLOT;
	}
	private int Kth_child(int idx, int k) {
		return (idx * SLOT) + k;
	}
	private int Max_child(int idx) {
		int lhs = Kth_child(idx, 1);
		int rhs = Kth_child(idx, 2);
		int comp = ComparerAble_.Compare(comparer, heap[lhs], heap[rhs]);
		boolean diff = is_max ? comp == CompareAble_.More : comp == CompareAble_.Less;
		return diff ? lhs : rhs;
	}
	private void Heapify_up(int idx) {
		Object val = heap[idx];
		while (idx > 0) {
			int comp = ComparerAble_.Compare(comparer, val, heap[Parent(idx)]);
			if (!(is_max ? comp == CompareAble_.More : comp == CompareAble_.Less))
				break;
			heap[idx] = heap[Parent(idx)];
			idx = Parent(idx);
		}
		heap[idx] = val;
	}
	private void Heapify_down(int idx) {
		int child;
		Object val = heap[idx];
		while (Kth_child(idx, 1) < size) {
			child = Max_child(idx);
			int comp = ComparerAble_.Compare(comparer, val, heap[child]);
			if (is_max ? comp == CompareAble_.Less : comp == CompareAble_.More)
				heap[idx] = heap[child];
			else
				break;
			idx = child;
		}
		heap[idx] = val;
	}
	private static final int SLOT = 2;
}

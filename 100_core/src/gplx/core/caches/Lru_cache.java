/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.caches;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
public class Lru_cache {
	private final Hash_adp map = Hash_adp_.New();
	private Lru_node head, tail;
	private long cur, min, max, evicts;
	public Lru_cache(boolean auto_reg, String key, long min, long max) {
		this.key = key;
		this.min = min;
		this.max = max;
		if (auto_reg) Lru_cache_root.Instance.Add(this);
	}
	public String Key() {return key;} private final String key;
	public long Evicts() {return evicts;}
	public long Cur() {return cur;}
	public void Min_max_(long min, long max) {
		this.min = min;
		this.max = max;
	}
	public Object Get_or_null(Object key) {
		Lru_node nde = (Lru_node)map.GetByOrNull(key);
		if (nde == null) {
			return null;
		}

		Del_node_from_linked_list(nde);
		Add_to_tail(nde);

		return nde.Val();
	}
	public void Set(Object key, Object val, long size) {
		Lru_node nde = (Lru_node)map.GetByOrNull(key);
		if (nde != null) {
			nde.Val_(val);

			Del_node_from_linked_list(nde);
			Add_to_tail(nde);
		}
		else {
			this.Clear_min(size);

			nde = new Lru_node(key, val, size);
			Add_to_tail(nde);
			map.Add(key, nde);
			cur += size;
		}
	}
	public void Del(Object key) {
		Lru_node nde = (Lru_node)map.GetByOrNull(key);
		if (nde != null) {
			Del_node_from_this(nde);
		}
	}
	public void Clear_all() {
		synchronized (map) {
		map.Clear();
		head = null;
		tail = null;
		cur = 0;
		}
	}
	public void Clear_min(long size) {
		synchronized (map) {
		long threshold = min >= 0 ? min : max;
		while (cur + size > threshold) {
			Del_node_from_this(head);
			evicts++;
		}
		}
	}
	private void Del_node_from_this(Lru_node nde) {
		synchronized (map) {
		map.Del(nde.Key());
		cur -= nde.Size();
		Del_node_from_linked_list(nde);
		}
	}
	private void Del_node_from_linked_list(Lru_node nde) {
		synchronized (map) {
		if (nde.Prv() == null)
			head = nde.Nxt();
		else
			nde.Prv().Nxt_(nde.Nxt());

		if (nde.Nxt() == null)
			tail = nde.Prv();
		else
			nde.Nxt().Prv_(nde.Prv());
		}
	}
	private void Add_to_tail(Lru_node nde) {
		synchronized (map) {
		if (tail != null)
			tail.Nxt_(nde);

		nde.Prv_(tail);
		nde.Nxt_(null);
		tail = nde;

		if (head == null)
			head = tail;
		}
	}
	public void To_str(BryWtr bfr, boolean grps_only_or_both) {
		bfr.AddStrA7("g");
		bfr.AddBytePipe().AddStrU8(key);
		bfr.AddBytePipe().AddLongVariable(cur);
		bfr.AddBytePipe().AddLongVariable(min);
		bfr.AddBytePipe().AddLongVariable(max);
		bfr.AddByteNl();
		if (grps_only_or_both) {
			Lru_node nde = head;
			while (nde != null) {
				nde.To_str(bfr);
				nde = nde.Nxt();
			}
		}
	}
}
class Lru_node {
	private final Object key;
	private Object val;
	private final long size;
	private Lru_node prv;
	private Lru_node nxt;

	public Lru_node(Object key, Object val, long size) {
		this.key = key;
		this.val = val;
		this.size = size;
	}
	public Object Key() {return key;}
	public Object Val() {return val;} public void Val_(Object v) {this.val = v;}
	public long Size() {return size;}
	public Lru_node Prv() {return prv;} public void Prv_(Lru_node v) {this.prv = v;}
	public Lru_node Nxt() {return nxt;} public void Nxt_(Lru_node v) {this.nxt = v;}
	public void To_str(BryWtr bfr) {
		bfr.AddStrA7("i");
		bfr.AddBytePipe().AddStrU8(ObjectUtl.ToStrOrNullMark(key));
		bfr.AddBytePipe().AddLongVariable(size);
		bfr.AddByteNl();
	}
}

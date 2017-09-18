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
import org.junit.*; import gplx.core.tests.*;
public class Queue_adp_tst {
	private final    Queue_adp_fxt fxt = new Queue_adp_fxt();
	@Test  public void Empty() {
		boolean pass = true;
		try {
			fxt.Test__dequeue(null, -1);
			pass = false;
		} catch (Exception e) {
			Err_.Noop(e);
			return;
		}
		if (pass) throw Err_.new_wo_type("empty should have failed");
	}
	@Test  public void Add_1() {
		fxt.Exec__enqueue("a");
		fxt.Test__dequeue("a", 0);
	}
	@Test  public void Add_n() {
		fxt.Exec__enqueue("a");
		fxt.Exec__enqueue("b");
		fxt.Exec__enqueue("c");
		fxt.Test__dequeue("a", 2);
		fxt.Test__dequeue("b", 1);
		fxt.Test__dequeue("c", 0);
	}
	@Test  public void Mix() {
		fxt.Exec__enqueue("a");
		fxt.Exec__enqueue("b");
		fxt.Test__dequeue("a", 1);
		fxt.Exec__enqueue("c");
		fxt.Test__dequeue("b", 1);
		fxt.Test__dequeue("c", 0);
	}
}
class Queue_adp_fxt {
	private final    Queue_adp queue = new Queue_adp();
	public void Exec__enqueue(String s) {queue.Enqueue(s);}
	public void Test__dequeue(String expd_data, int expd_len) {
		Gftest.Eq__str(expd_data, (String)queue.Dequeue());
		Gftest.Eq__int(expd_len , queue.Count());
	}
}

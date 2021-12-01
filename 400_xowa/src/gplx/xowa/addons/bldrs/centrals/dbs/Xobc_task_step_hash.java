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
package gplx.xowa.addons.bldrs.centrals.dbs; import gplx.*;
public class Xobc_task_step_hash {
	private final Ordered_hash tasks_hash = Ordered_hash_.New();
	private final Ordered_hash steps_hash = Ordered_hash_.New();
	public int Tasks__len() {return tasks_hash.Len();}
	public int Tasks__get_at(int i) {return Int_.Cast(tasks_hash.Get_at(i));}
	public int Steps__len() {return steps_hash.Len();}
	public int Steps__get_at(int i) {return Int_.Cast(steps_hash.Get_at(i));}
	public void Clear() {tasks_hash.Clear(); steps_hash.Clear();}
	public void Add(int task_id, int step_id) {
		tasks_hash.AddIfDupeUseNth(task_id, task_id);
		steps_hash.AddIfDupeUseNth(step_id, step_id);
	}
}

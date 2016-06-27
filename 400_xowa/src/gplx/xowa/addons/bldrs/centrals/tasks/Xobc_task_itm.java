/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.bldrs.centrals.tasks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*;
import gplx.core.gfobjs.*;
import gplx.xowa.addons.bldrs.centrals.cmds.*; import gplx.xowa.addons.bldrs.centrals.steps.*;
public class Xobc_task_itm implements gplx.CompareAble {
	public Xobc_task_itm(int task_id, int task_seqn, int step_count, String task_key, String task_name) {
		this.task_id = task_id; this.task_seqn = task_seqn; 
		this.step_count = step_count;
		this.task_key = task_key;
		this.task_name = task_name;
	}
	public int				Task_id()		{return task_id;} private final    int task_id;
	public String			Task_key()		{return task_key;} private final    String task_key;
	public String			Task_name()		{return task_name;} private final    String task_name;
	public int				Step_count()	{return step_count;} private final    int step_count;
	public int				Task_seqn()		{return task_seqn;} private int task_seqn;
	public byte				Task_status()	{return task_status;} private byte task_status;
	public Xobc_step_itm	Step()			{return step;} public void Step_(Xobc_step_itm v) {this.step = v;} private Xobc_step_itm step;
	public boolean				Step_is_last()	{return step.Step_seqn() == step_count - 1;}
	public void				Task_status_(byte v) {task_status = v;}		// called when task moves from init -> working -> suspended -> done
	public void				Task_seqn_(int v) {this.task_seqn = v;}		// called when task is init'd from db, or added to list
	public int				compareTo(Object obj) {Xobc_task_itm comp = (Xobc_task_itm)obj; return Int_.Compare(task_seqn, comp.task_seqn);}

	public Gfobj_nde Save_to(Gfobj_nde nde) {
		nde.Add_int	("task_id"				, task_id);
		nde.Add_str	("task_name"			, task_name);
		nde.Add_byte("task_status"			, task_status);
		if (step != null) {
			step.Save_to(nde.New_nde("step"));
		}
		return nde;	// FLUENT
	}
}

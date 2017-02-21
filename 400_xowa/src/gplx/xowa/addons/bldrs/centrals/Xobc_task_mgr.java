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
package gplx.xowa.addons.bldrs.centrals; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*;
import gplx.core.brys.evals.*; import gplx.core.gfobjs.*; import gplx.core.progs.rates.*; import gplx.core.threads.*;
import gplx.xowa.addons.bldrs.centrals.tasks.*; import gplx.xowa.addons.bldrs.centrals.steps.*; import gplx.xowa.addons.bldrs.centrals.cmds.*; import gplx.xowa.addons.bldrs.centrals.dbs.*; import gplx.xowa.addons.bldrs.centrals.mgrs.*;
import gplx.xowa.guis.cbks.*;
public class Xobc_task_mgr implements Xog_json_wkr {
	private final    Xog_cbk_trg cbk_trg = Xog_cbk_trg.New(Xobc_task_special.Prototype.Special__meta().Ttl_bry());
	public Xobc_task_mgr(Xoa_app app, Io_url data_db_url) {
		this.app = app;
		this.cbk_mgr = app.Gui__cbk_mgr();
		this.user_db = new Xobc_user_db(app.User().User_db_mgr().Conn());
		this.data_db = new Xobc_data_db(data_db_url);
		this.work_mgr = new Xobc_task_regy__work(this, app);
		this.todo_mgr = new Xobc_task_regy__todo(this);
		this.done_mgr = new Xobc_task_regy__done(this);
		this.step_mgr = new Xobc_step_factory(this, data_db, app.Fsys_mgr().Wiki_dir());
		this.skip_mgr = new Xobc_skip_mgr(this, app);
		this.ary = new Xobc_task_regy__base[] {work_mgr, todo_mgr, done_mgr};
		this.rate_mgr = Xobc_cmd__base.New_rate_mgr();
	}
	public Xoa_app						App()		{return app;}		private final    Xoa_app app;
	public Xog_cbk_mgr					Cbk_mgr()	{return cbk_mgr;}	private final    Xog_cbk_mgr cbk_mgr;
	public Xobc_task_regy__work			Work_mgr()	{return work_mgr;}	private final    Xobc_task_regy__work work_mgr;
	public Xobc_task_regy__todo			Todo_mgr()	{return todo_mgr;}	private final    Xobc_task_regy__todo todo_mgr;
	public Xobc_task_regy__done			Done_mgr()	{return done_mgr;}	private final    Xobc_task_regy__done done_mgr;
	public Xobc_task_regy__base			Get_at(int i) {return ary[i];}	private final    Xobc_task_regy__base[] ary;
	public Xobc_data_db					Data_db()   {return data_db;}	private final    Xobc_data_db data_db;
	public Xobc_user_db					User_db()   {return user_db;}	private final    Xobc_user_db user_db;
	public Gfo_rate_mgr					Rate_mgr()	{return rate_mgr;}	private final    Gfo_rate_mgr rate_mgr;
	public Xobc_step_factory			Step_mgr()	{return step_mgr;}	private final    Xobc_step_factory step_mgr;
	public Xobc_filter_mgr				Filter_mgr() {return filter_mgr;} private final    Xobc_filter_mgr filter_mgr = new Xobc_filter_mgr();
	public Xobc_skip_mgr				Skip_mgr()	{return skip_mgr;}	private final    Xobc_skip_mgr skip_mgr;
	public Xobc_lang_mgr				Lang_mgr() {return lang_mgr;} private final    Xobc_lang_mgr lang_mgr = new Xobc_lang_mgr();
	public void Send_json(String func, Gfobj_nde data) {cbk_mgr.Send_json(cbk_trg, func, data);}
	public Xobc_task_mgr Load_or_init() {
		Gfo_log_.Instance.Info("task_mgr.load_or_init.bgn");
		data_db.Tbl__task_regy().Select_all(todo_mgr);
		user_db.Work_task_tbl().Select_all(this, todo_mgr, work_mgr);
		user_db.Done_task_tbl().Select_all(todo_mgr, done_mgr);
		return this;
	}
	public void Reload() {
		Gfo_log_.Instance.Info("task_mgr.reload.bgn");
		Gfobj_nde root = Gfobj_nde.New();
		Gfobj_nde lists_nde = root.New_nde("lists");
		work_mgr.Save_to(lists_nde.New_ary("work"));
		todo_mgr.Save_to(lists_nde.New_ary("todo"), filter_mgr.Filter(todo_mgr));
		done_mgr.Save_to(lists_nde.New_ary("done"));
		root.Add_nde("filters", filter_mgr.Make_init_msg());
		root.Add_ary("langs", lang_mgr.Make_init_msg(data_db.Tbl__lang_regy().Select_all()));
		cbk_mgr.Send_json(cbk_trg, "xo.bldr.core.reload__recv", root);
	}
	public void Filter_todo(String lang_key, String type_key) {
		Gfo_log_.Instance.Info("task_mgr.filter_by_lang.bgn");
		Gfobj_nde root = Gfobj_nde.New();
		Gfobj_nde lists_nde = root.New_nde("lists").Add_str("list_name", "todo");
		todo_mgr.Save_to(lists_nde.New_ary("todo"), filter_mgr.Filter(todo_mgr, lang_key, type_key));
		cbk_mgr.Send_json(cbk_trg, "xo.bldr.core.reload_list__recv", root);
	}
	public void Transfer(Xobc_task_regy__base src, Xobc_task_regy__base trg, Xobc_task_itm task) {
		Gfo_log_.Instance.Info("task_mgr.transfer.bgn", "task_uid", task.Task_id(), "src", src.Name(), "trg", trg.Name());
		src.Del_by(task.Task_id());
		trg.Add(task);
		cbk_mgr.Send_json(cbk_trg, "xo.bldr.core.transfer__recv", Gfobj_nde.New().Add_str("src", src.Name()).Add_str("trg", trg.Name()).Add_nde("task", task.Save_to(Gfobj_nde.New())));
	}
	public static final int Lists_len = 3;
}

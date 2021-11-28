package gplx.gfdbs.cores.wkrs;

import gplx.gfdbs.cores.GfdbState;
import gplx.gfdbs.cores.GfdbItm;
import gplx.gfdbs.cores.cmds.GfdbModifyCmd;
import gplx.gfdbs.cores.cmds.GfdbSelectCmd;
import gplx.objects.lists.GfoIndexedList;
import gplx.objects.lists.GfoListBase;

public class GfdbListWkr<K, I extends GfdbItm<K, I>> {
    private final GfoIndexedList<K, I> regy;
	private final GfdbModifyCmd<I> insertCmd;
	private final GfdbModifyCmd<I> updateCmd;
	private final GfdbModifyCmd<I> deleteCmd;
	private final GfdbSelectCmd<I> selectCmd;
	private final Object[] selectArgs;
	public GfdbListWkr(GfoIndexedList<K, I> regy,
					   GfdbModifyCmd<I> insertCmd, GfdbModifyCmd<I> updateCmd, GfdbModifyCmd<I> deleteCmd,
					   GfdbSelectCmd<I> selectCmd, Object[] selectArgs) {
	    this.regy = regy;
	    this.selectCmd = selectCmd;
	    this.selectArgs = selectArgs;
	    this.insertCmd = insertCmd;
	    this.updateCmd = updateCmd;
	    this.deleteCmd = deleteCmd;
	}
	public void Load() {
		GfoListBase<I> dbList = selectCmd.Select(selectArgs);
		for (I regyItm : regy) {
			regyItm.DbStateSet(GfdbState.Insert);
		}
		for (I dbItm : dbList) {
			K key = dbItm.ToPkey();
			I regyItm = regy.GetByOrNull(key);
			if (regyItm == null) {
				dbItm.DbStateSet(GfdbState.Delete);
			}
			else {
				regyItm.CtorByItm(dbItm);
				regyItm.DbStateSet(GfdbState.Update);
				dbItm.DbStateSet(GfdbState.Noop);
			}
		}
		for (I itm : regy) {
			SaveItm(itm);
		}
		for (I itm : dbList) {
			SaveItm(itm);
		}
	}
	public void Save() {
		for (I itm : regy) {
			SaveItm(itm);
		}
	}
	private void SaveItm(I itm) {
		GfdbState itmState = itm.DbState();
		itm.DbStateSet(GfdbState.Noop);
		switch (itmState) {
			case Insert: insertCmd.Exec(itm); break;
			case Update: updateCmd.Exec(itm); break;
			case Delete: deleteCmd.Exec(itm); break;
		}
	}
}

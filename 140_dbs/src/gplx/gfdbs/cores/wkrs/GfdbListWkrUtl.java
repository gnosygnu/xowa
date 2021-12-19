package gplx.gfdbs.cores.wkrs;

import gplx.gfdbs.cores.GfdbItm;
import gplx.gfdbs.cores.GfdbMeta;
import gplx.gfdbs.cores.GfdbTbl;
import gplx.gfdbs.cores.cmds.GfdbSelectCmd;
import gplx.types.commons.lists.GfoIndexedList;

public class GfdbListWkrUtl {
	public static <K, I extends GfdbItm<K, I>, M extends GfdbMeta<I>, T extends GfdbTbl<I, M>>
	GfdbListWkr<K, I> NewByTbl(GfoIndexedList<K, I> regy, T tbl, GfdbSelectCmd<I> selectCmd, Object... selectArgs) {
        return new GfdbListWkr<>(regy, tbl.InsertCmd(), tbl.UpdateCmd(), tbl.DeleteCmd(), selectCmd, selectArgs);
	}
}

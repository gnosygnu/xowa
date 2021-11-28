package gplx.gfdbs.cores.cmds;

import gplx.gfdbs.cores.GfdbTbl;
import gplx.gfdbs.cores.GfdbMeta;
import gplx.gfdbs.cores.GfdbState;
import gplx.objects.lists.GfoIndexedList;

public class GfoDbModifyCmdHash {
	private final GfoIndexedList<String, GfdbModifyCmd<Object>> hash = new GfoIndexedList<>();

    public <I extends Object> GfoDbModifyCmdHash Add(GfdbModifyCmd<I> cmd) {
    	hash.Add(ToKey(cmd.Meta().TblName(), cmd.DbState()), (GfdbModifyCmd<Object>)cmd);
    	return this;
    }
    public <I, G extends GfdbMeta<I>> GfoDbModifyCmdHash AddTbl(GfdbTbl<I, G> tbl) {
        Add((GfdbModifyCmd<Object>)tbl.InsertCmd());
        Add((GfdbModifyCmd<Object>)tbl.UpdateCmd());
        Add((GfdbModifyCmd<Object>)tbl.DeleteCmd());
        return this;
    }
    private String ToKey(String type, GfdbState state) {
    	return type + "|" + state.name();
    }
}

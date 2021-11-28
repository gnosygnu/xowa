package gplx.gfdbs.cores.cmds;

import gplx.gfdbs.cores.GfdbCloseAble;
import gplx.objects.lists.GfoListBase;

public interface GfdbSelectList<I> extends GfdbCloseAble {
    GfoListBase<I> Select(Object... args);
}

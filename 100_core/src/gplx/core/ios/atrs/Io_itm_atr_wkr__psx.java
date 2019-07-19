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
package gplx.core.ios.atrs; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
class Io_itm_atr_wkr__psx extends Io_itm_atr_wkr {		private final PosixFileAttributeView view;
	public Io_itm_atr_wkr__psx(Path path) {
		super(path);
		this.view = Files.getFileAttributeView(path, PosixFileAttributeView.class);
	}
		@Override public boolean Is_read_only() {
				try {
			// ASSUME:a file is read-only if it is read-only; Note that the directory may need write-access, but not handling it now; REF:https://superuser.com/a/114611
			Set<PosixFilePermission> perms = view.readAttributes().permissions();
		    int perm_flag = Psx__permissions_to_int(perms);
		    return perm_flag == 0444;
		} catch (Exception e) {
			throw Err_.new_exc(e, "", "Is_read_only failed", "e", Err_.Message_lang(e));
		}
			}
		public static int Psx__permissions_to_int(Set<PosixFilePermission> psx_perms) {
	    int rv = 0;
	    rv |= ((psx_perms.contains(PosixFilePermission.OWNER_READ))     ? 1 << 8 : 0);
	    rv |= ((psx_perms.contains(PosixFilePermission.OWNER_WRITE))    ? 1 << 7 : 0);
	    rv |= ((psx_perms.contains(PosixFilePermission.OWNER_EXECUTE))  ? 1 << 6 : 0);
	    rv |= ((psx_perms.contains(PosixFilePermission.GROUP_READ))     ? 1 << 5 : 0);
	    rv |= ((psx_perms.contains(PosixFilePermission.GROUP_WRITE))    ? 1 << 4 : 0);
	    rv |= ((psx_perms.contains(PosixFilePermission.GROUP_EXECUTE))  ? 1 << 3 : 0);
	    rv |= ((psx_perms.contains(PosixFilePermission.OTHERS_READ))    ? 1 << 2 : 0);
	    rv |= ((psx_perms.contains(PosixFilePermission.OTHERS_WRITE))   ? 1 << 1 : 0);
	    rv |= ((psx_perms.contains(PosixFilePermission.OTHERS_EXECUTE)) ? 1      : 0);
	    return rv;
	}
	}

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
package gplx.core.ios.atrs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Test;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.util.HashSet;
import java.util.Set;
public class Io_itm_atr_wkr__acl__tst {
	private final Io_itm_attrib_wkr__acl__fxt fxt = new Io_itm_attrib_wkr__acl__fxt();
	@Test public void Perm_exists() {
		fxt.Test__Is_permitted
		( BoolUtl.Y, AclEntryPermission.WRITE_DATA
		, fxt.Make__acl("Everyone", AclEntryType.ALLOW, AclEntryPermission.WRITE_DATA)
		);
	}
	@Test public void Perm_missing() {
		fxt.Test__Is_permitted
		( BoolUtl.N, AclEntryPermission.WRITE_DATA
		, fxt.Make__acl("Everyone", AclEntryType.ALLOW, AclEntryPermission.READ_DATA)
		);
	}
	@Test public void Deny_over_allow() {
		fxt.Test__Is_permitted
		( BoolUtl.N, AclEntryPermission.WRITE_DATA
		, fxt.Make__acl("Everyone", AclEntryType.ALLOW, AclEntryPermission.WRITE_DATA)
		, fxt.Make__acl("Everyone", AclEntryType.DENY , AclEntryPermission.WRITE_DATA)
		);
	}
	@Test public void Same_principal__perm_missing_over_perm_exists() {
		/*
		EX: SD card wherein acl_list has 2 entries
		* Entry[0] | //MACHINE/SHARE | Everyone:READ_DATA/READ_NAMED_ATTRS/EXECUTE/READ_ATTRIBUTES/READ_ACL/SYNCHRONIZE:ALLOW
		* Entry[1] | DRIVE_NAME:/    | Everyone:READ_DATA/WRITE_DATA/APPEND_DATA/READ_NAMED_ATTRS/WRITE_NAMED_ATTRS/EXECUTE/DELETE_CHILD/READ_ATTRIBUTES/WRITE_ATTRIBUTES/DELETE/READ_ACL/WRITE_ACL/WRITE_OWNER/SYNCHRONIZE:ALLOW
		*/
		fxt.Test__Is_permitted
		( BoolUtl.N, AclEntryPermission.WRITE_DATA
		, fxt.Make__acl("Everyone", AclEntryType.ALLOW, AclEntryPermission.READ_DATA)
		, fxt.Make__acl("Everyone", AclEntryType.ALLOW, AclEntryPermission.READ_DATA, AclEntryPermission.WRITE_DATA)
		);
	}
	@Test public void Diff_principals__perm_exists_over_perm_missing() {
		/*
		EX: C drive wherein acl_list has 2 entries
		* Entry[0] | C:/ | Administrators:READ_DATA/WRITE_DATA
		* Entry[1] | C:/ | Everyone:READ_DATA
		*/
		fxt.Test__Is_permitted
		( BoolUtl.Y, AclEntryPermission.WRITE_DATA
		, fxt.Make__acl("Administrators", AclEntryType.ALLOW, AclEntryPermission.WRITE_DATA)
		, fxt.Make__acl("Users"         , AclEntryType.ALLOW, AclEntryPermission.READ_DATA)
		);
	}
}
class Io_itm_attrib_wkr__acl__fxt {
	public void Test__Is_permitted(boolean expd, AclEntryPermission permission, Acl_entry... entries) {
		boolean actl = Io_itm_atr_wkr__acl.Is_permitted(entries, permission);
		GfoTstr.Eq(expd, actl);
	}
	public Acl_entry Make__acl(String principal, AclEntryType type, AclEntryPermission... perms) {
		Set<AclEntryPermission> perm_set = new HashSet<AclEntryPermission>();
		for (AclEntryPermission perm : perms)
			perm_set.add(perm);
		return new Acl_entry(principal, type, perm_set);
	}
}

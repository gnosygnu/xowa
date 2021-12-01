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
package gplx.core.ios.atrs; import gplx.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.util.List;
import java.util.Set;

import gplx.core.primitives.Bool_obj_val;
class Io_itm_atr_wkr__acl extends Io_itm_atr_wkr {		private final AclFileAttributeView view;
	public Io_itm_atr_wkr__acl(Path path) {
		super(path);
		this.view = Files.getFileAttributeView(path, AclFileAttributeView.class);
	}
		@Override public boolean Is_read_only() {
				try {
			// convert AclEntry to Acl_entry 
			List<AclEntry> list = view.getAcl();
			int len = list.size();
			Acl_entry[] ary = new Acl_entry[len];
			for (int i = 0; i < len; i++) {
				AclEntry under = list.get(i);
				ary[i] = new Acl_entry(under.principal().toString(), under.type(), under.permissions());
			}
			return !Is_permitted(ary, AclEntryPermission.WRITE_DATA);
		} catch (Exception e) {
			throw Err_.new_exc(e, "", "Is_read_only failed", "e", Err_.Message_lang(e));
		}
			}
		public static boolean Is_permitted(Acl_entry[] ary, AclEntryPermission permission) {
		boolean rv = false;
		Hash_adp principals = Hash_adp_.New();
		for (Acl_entry itm : ary) {
			Set<AclEntryPermission> permissions = itm.Permissions();
			switch (itm.Type()) {
				// If multiple ALLOW entries
				// * for same principal, return false if any of them do not have permission
				// * for diff principals, return true if any of them does have permissions
				case ALLOW: {
					// if current principal is forbidden, ignore entry; want to skip lists like Everyone:Forbidden:C:/folder;Everyone:Allowed;C:/
					Bool_obj_val forbidden = (Bool_obj_val)principals.GetByOrNull(itm.Principal());
					if (forbidden != null) {
						continue;
					}
					if (!permissions.contains(permission) && !rv) {
						rv = false;
						principals.Add(itm.Principal(), Bool_obj_val.False);
					}
					else {
						rv = true;
					}
					break;
				}
				// If any DENY entries, return false on first entry
				case DENY: {
					if (permissions.contains(permission)) {
						return false;
					}
					break;
				}
			}
		}
		return rv;
	}
	}
class Acl_entry {
	public Acl_entry(String principal, AclEntryType type, Set<AclEntryPermission> permissions) {
		this.principal = principal;
		this.type = type;
		this.permissions = permissions;
	}
	public String Principal() {return principal;} private final String principal;
	public AclEntryType Type() {return type;} private final AclEntryType type;
	public Set<AclEntryPermission> Permissions() {return permissions;} private final Set<AclEntryPermission> permissions;
}
//#}
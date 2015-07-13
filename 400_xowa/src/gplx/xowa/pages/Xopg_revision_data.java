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
package gplx.xowa.pages; import gplx.*; import gplx.xowa.*;
public class Xopg_revision_data {
	public int		Id() {return id;} public Xopg_revision_data Id_(int v) {id = v; return this;} private int id;
	public DateAdp	Modified_on() {return modified_on;} public Xopg_revision_data Modified_on_(DateAdp v) {modified_on = v; return this;} private DateAdp modified_on = DateAdp_.MinValue;
	public byte[]	User() {return user;} public Xopg_revision_data User_(byte[] v) {user = v; return this;} private byte[] user = Bry_.Empty;
	public byte[]	Protection_level() {return protection_level;} public Xopg_revision_data Protection_level_(byte[] v) {protection_level = v; return this;} private byte[] protection_level = Bry_.Empty;
	public int		Html_db_id() {return html_db_id;} public void Html_db_id_(int v) {html_db_id = v;} private int html_db_id = -1;
	public void		Clear()	{}// NOTE: do not clear data b/c saving in Edit will call clear and id will be reset to 0
}

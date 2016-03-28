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
package gplx.core.criterias; import gplx.*; import gplx.core.*;
public class Criteria_not implements Criteria {
	public Criteria_not(Criteria v) {this.criteria = v;}
	public byte				Tid() {return Criteria_.Tid_not;}
	public boolean				Matches(Object obj) {return !criteria.Matches(obj);}
	public void				Val_from_args(Hash_adp args) {criteria.Val_from_args(args);}
	public void				Val_as_obj_(Object v) {criteria.Val_as_obj_(v);}
	public String			To_str() {return String_.Concat_any(" NOT ", criteria.To_str());}
	public Criteria			Crt() {return criteria;} private final Criteria criteria;
}

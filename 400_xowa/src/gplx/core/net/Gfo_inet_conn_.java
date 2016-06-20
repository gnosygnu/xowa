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
package gplx.core.net; import gplx.*; import gplx.core.*;
public class Gfo_inet_conn_ {
	public static final int Tid__http = 1, Tid__mem__hash = 2, Tid__mem__pile = 3;
	public static Gfo_inet_conn new_http()		{return new Gfo_inet_conn__http();}
	public static Gfo_inet_conn new_mem_hash()	{return new Gfo_inet_conn__mem__hash();}
	public static Gfo_inet_conn new_mem_pile()	{return new Gfo_inet_conn__mem__pile();}
	public static Gfo_inet_conn new_()	{
		switch (new_prototype) {
			default:
			case Tid__http:			return new_http();
			case Tid__mem__hash:	return new_mem_hash();
			case Tid__mem__pile:	return new_mem_pile();
		}
	}
	public static void new_prototype_(int v) {new_prototype = v;} private static int new_prototype = Tid__http;
}
class Gfo_inet_conn__mem__hash implements Gfo_inet_conn {
	private final    Hash_adp hash = Hash_adp_.New();
	public int				Tid() {return Gfo_inet_conn_.Tid__mem__hash;}
	public void				Clear() {hash.Clear();}
	public void				Upload_by_bytes(String url, byte[] data) {hash.Add(url, data);}
	public byte[]			Download_as_bytes_or_null(String url) {return (byte[])hash.Get_by(url);}
}
class Gfo_inet_conn__mem__pile implements Gfo_inet_conn {
	private final    List_adp pile = List_adp_.New();
	public int				Tid() {return Gfo_inet_conn_.Tid__mem__hash;}
	public void				Clear() {pile.Clear();}
	public void				Upload_by_bytes(String url, byte[] data) {pile.Add(data);}
	public byte[]			Download_as_bytes_or_null(String url) {return (byte[])List_adp_.Pop_last(pile);}
}

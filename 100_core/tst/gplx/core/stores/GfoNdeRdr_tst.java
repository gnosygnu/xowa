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
package gplx.core.stores; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.gfo_ndes.*;
public class GfoNdeRdr_tst {
	@Test  public void Subs_leafs() {
		root = 
			fx.root_
			(	fx.row_vals_(0)
			,	fx.row_vals_(1)
			,	fx.row_vals_(2)
			);
		tst_NdeVals(root, 0, 1, 2);
	}
	@Test  public void Subs_ndes() {
		root =
			fx.root_
			(	leaf_("", 0)
			,	leaf_("", 1)
			,	leaf_("", 2)
			);
		tst_NdeVals(root, 0, 1, 2);
	}
	@Test  public void Subs_mix() {
		root = 
			fx.root_
			(	leaf_("", 0)
			,	fx.row_vals_(1)
			,	fx.row_vals_(2)
			);
		tst_NdeVals(root, 0, 1, 2);
	}
	@Test  public void Subs_rdr() {
		root = 
			fx.root_
			(	fx.row_vals_(0)
			);
		rootRdr = GfoNdeRdr_.root_parseNot_(root);
		DataRdr rdr = rootRdr.Subs();
		Tfds.Eq_true(rdr.MoveNextPeer());
		Tfds.Eq(0, rdr.ReadAt(0));
		Tfds.Eq_false(rdr.MoveNextPeer());
	}
	@Test  public void MoveNextPeer_implicit() {
		root = 
			fx.root_
			(	fx.csv_dat_
			(		fx.row_vals_(0)
			,		fx.row_vals_(1)
			,		fx.row_vals_(2)
			)
			);
		GfoNdeRdr rootRdr = GfoNdeRdr_.root_parseNot_(root);
		DataRdr subsRdr = rootRdr.Subs();	// pos=-1; bof
		DataRdr subRdr = subsRdr.Subs();	// MoveNextPeer not needed; implicitly moves to pos=0
		tst_RdrVals(subRdr, Object_.Ary(0, 1, 2));
	}
	@Test  public void MoveNextPeer_explicit() {
		root =
			fx.root_
			(	fx.csv_dat_
			(		fx.row_vals_(0)
			,		fx.row_vals_(1)
			,		fx.row_vals_(2)
			)
			);
		GfoNdeRdr rootRdr = GfoNdeRdr_.root_parseNot_(root);
		DataRdr subsRdr = rootRdr.Subs();		// pos=-1; bof
		Tfds.Eq_true(subsRdr.MoveNextPeer());	// explicitly moves to pos=0
		DataRdr subRdr = subsRdr.Subs();
		tst_RdrVals(subRdr, Object_.Ary(0, 1, 2));
	}
	@Test  public void Xpath_basic() {
		root = fx.root_
			(	leaf_("root", 0)
			,	leaf_("root", 1)
			,	leaf_("root", 2)
			);
		tst_Xpath_all(root, "root", 0, 1, 2);
	}
	@Test  public void Xpath_nested() {
		root = fx.root_
			(	fx.tbl_("owner"
			,		leaf_("root", 0)
			,		leaf_("root", 1)
			,		leaf_("root", 2)
			));
		tst_Xpath_all(root, "owner/root", 0, 1, 2);
	}
	@Test  public void Xpath_null() {
		root = fx.root_
			(	leaf_("match", 0)
			);
		rootRdr = GfoNdeRdr_.root_parseNot_(root);
		DataRdr sub = rootRdr.Subs_byName("no_match");
		Tfds.Eq_false(sub.MoveNextPeer());
	}
	@Test  public void Xpath_moveFirst_basic() {
		root = fx.root_
			(	leaf_("nde0", 0)
			);
		tst_Xpath_first(root, "nde0", 0);
	}
	@Test  public void Xpath_moveFirst_shallow() {
		root = fx.root_
			(	leaf_("nde0", 0)
			,	leaf_("nde1", 1)
			,	leaf_("nde2", 2)
			);
		tst_Xpath_first(root, "nde2", 2);
	}
	@Test  public void Xpath_moveFirst_nested() {
		root = fx.root_
			(	node_("nde0", Object_.Ary("0")
			,		leaf_("nde00", "00")
			));
		tst_Xpath_first(root, "nde0", "0");
		tst_Xpath_first(root, "nde0/nde00", "00");
	}
	@Test  public void Xpath_moveFirst_nested_similarName() {
		root = fx.root_
			(	node_("nde0", Object_.Ary("0")
			,		leaf_("nde00", "00")
			)
			,	node_("nde1", Object_.Ary("1")
			,		leaf_("nde00", "10")
			));
		tst_Xpath_first(root, "nde1/nde00", "10");
	}
	@Test  public void Xpath_moveFirst_many() {
		root = fx.root_
			(	leaf_("root", 0)
			,	leaf_("root", 1)
			,	leaf_("root", 2)
			);
		tst_Xpath_first(root, "root", 0);	// returns first
	}
	@Test  public void Xpath_moveFirst_null() {
		root = fx.root_
			(	leaf_("nde0", 0)
			,	leaf_("nde1", 1)
			,	leaf_("nde2", 2)
			);
		rootRdr = GfoNdeRdr_.root_parseNot_(root);
		DataRdr rdr = rootRdr.Subs_byName("nde3");
		Tfds.Eq_false(rdr.MoveNextPeer());
	}

	GfoNde leaf_(String name, Object... vals)						{return GfoNde_.nde_(name, vals, GfoNde_.Ary_empty);}
	GfoNde node_(String name, Object[] vals, GfoNde... subs)		{return GfoNde_.nde_(name, vals, subs);}
	void tst_NdeVals(GfoNde nde, Object... exptVals) {
		DataRdr rdr = GfoNdeRdr_.root_parseNot_(nde);
		tst_RdrVals(rdr.Subs(), exptVals);
	}
	void tst_RdrVals(DataRdr rdr, Object[] exptVals) {
		int count = 0;
		while (rdr.MoveNextPeer()) {
			Object actl = rdr.ReadAt(0);
			Tfds.Eq(actl, exptVals[count++]);
		}
		Tfds.Eq(count, exptVals.length);
	}
	void tst_Xpath_first(GfoNde root, String xpath, Object expt) {
		DataRdr rdr = GfoNdeRdr_.root_parseNot_(root);
		DataRdr sel = rdr.Subs_byName_moveFirst(xpath);
		Object actl = sel.ReadAt(0);
		Tfds.Eq(actl, expt);
	}
	void tst_Xpath_all(GfoNde root, String xpath, Object... exptVals) {
		DataRdr rdr = GfoNdeRdr_.root_parseNot_(root);
		tst_RdrVals(rdr.Subs_byName(xpath), exptVals);
	}
	GfoNde root; DataRdr rootRdr; GfoNdeFxt fx = GfoNdeFxt.new_();
}

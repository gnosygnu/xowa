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
package gplx.xowa.mediawiki.includes.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
/**
* Extends ArrayObject and does two things:
*
* Allows for deriving cla+sses to easily intercept additions
* and deletions for purposes such as additional indexing.
*
* Enforces the objects to be of a certain type, so this
* can be replied upon, much like if this had true support
* for generics, which sadly enough is not possible in PHP.
*/
public abstract class XomwGenericArrayObject extends XomwArrayObject {	/**
	* Returns the name of an interface/class that the element should implement/extend.
	*
	* @since 1.20
	*
	* @return String
	*/
	abstract public Class<?> getObjectType();

	/**
	* @see SiteList::getNewOffset()
	* @since 1.20
	* @var integer
	*/
	protected int indexOffset = 0;

	/**
	* Finds a new offset for when appending an element.
	* The super class does this, so it would be better to integrate,
	* but there does not appear to be any way to do this...
	*
	* @since 1.20
	*
	* @return integer
	*/
	protected int getNewOffset() {
		while (this.offsetExists(this.indexOffset)) {
			this.indexOffset++;
		}

		return this.indexOffset;
	}

	/**
	* Constructor.
	* @see ArrayObject::__construct
	*
	* @since 1.20
	*
	* @param null|array $input
	* @param int $flags
	* @param String $iterator_class
	*/
	public XomwGenericArrayObject() {
		//	if (input != null) {
		//		int len = Array_.Len(input);
		//		for (int i = 0; i < len; i++) {
		//			Object val = Array_.Get_at(input, i);
		//			this.offsetSet(i, val);
		//		}
		//	}
	}

	/**
	* @see ArrayObject::append
	*
	* @since 1.20
	*
	* @param mixed $value
	*/
	public void append(Object val) {
		this.setElement(XophpUtility.NULL_INT, val);
	}

	/**
	* @see ArrayObject::offsetSet()
	*
	* @since 1.20
	*
	* @param mixed $index
	* @param mixed $value
	*/
	@Override public void offsetSet(int index, Object val) {
		this.setElement(index, val);
	}

	/**
	* Returns if the provided value has the same type as the elements
	* that can be added to this ArrayObject.
	*
	* @since 1.20
	*
	* @param mixed $value
	*
	* @return boolean
	*/
	protected boolean hasValidType(Object val) {
		Class<?> cls = this.getObjectType();
		return Type_adp_.Eq_typeSafe(val, cls);
	}

	/**
	* Method that actually sets the element and holds
	* all common code needed for set operations, including
	* type checking and offset resolving.
	*
	* If you want to do additional indexing or have code that
	* otherwise needs to be executed whenever an element is added,
	* you can overload @see preSetElement.
	*
	* @since 1.20
	*
	* @param mixed $index
	* @param mixed $value
	*
	* @throws InvalidArgumentException
	*/
	protected void setElement(int index, Object val) {
		if (!this.hasValidType(val)) {
			throw new XophpInvalidArgumentException(
				"Can only add "	+ Type_adp_.FullNameOf_type(this.getObjectType()) + " implementing objects to "
				+ Type_adp_.ClassOf_obj(this) + "."
			);
		}

		if (XophpUtility.is_null(index)) {
			index = this.getNewOffset();
		}

		if (this.preSetElement(index, val)) {
			super.offsetSet(index, val);
		}
	}

	/**
	* Gets called before a new element is added to the ArrayObject.
	*
	* At this point the index is always set (ie not null) and the
	* value is always of the type returned by @see getObjectType.
	*
	* Should return a boolean. When false is returned the element
	* does not get added to the ArrayObject.
	*
	* @since 1.20
	*
	* @param integer|String $index
	* @param mixed $value
	*
	* @return boolean
	*/
	protected boolean preSetElement(int index, Object val) {
		return true;
	}

	//	/**
	//	* @see Serializable::serialize
	//	*
	//	* @since 1.20
	//	*
	//	* @return String
	//	*/
	//	public function serialize() {
	//		return serialize(this.getSerializationData());
	//	}
	//
	//	/**
	//	* Returns an array holding all the data that should go into serialization calls.
	//	* This is intended to allow overloading without having to reimplement the
	//	* behavior of this super class.
	//	*
	//	* @since 1.20
	//	*
	//	* @return array
	//	*/
	//	protected function getSerializationData() {
	//		return [
	//			'data' => this.getArrayCopy(),
	//			'index' => this.indexOffset,
	//		];
	//	}
	//
	//	/**
	//	* @see Serializable::unserialize
	//	*
	//	* @since 1.20
	//	*
	//	* @param String $serialization
	//	*
	//	* @return array
	//	*/
	//	public function unserialize($serialization) {
	//		$serializationData = unserialize($serialization);
	//
	//		foreach ($serializationData['data'] as $offset => $value) {
	//			// Just set the element, bypassing checks and offset resolving,
	//			// as these elements have already gone through this.
	//			parent::offsetSet($offset, $value);
	//		}
	//
	//		this.indexOffset = $serializationData['index'];
	//
	//		return $serializationData;
	//	}

	/**
	* Returns if the ArrayObject has no elements.
	*
	* @since 1.20
	*
	* @return boolean
	*/
	@gplx.Virtual public boolean isEmpty() {
		return this.count() == 0;
	}
}

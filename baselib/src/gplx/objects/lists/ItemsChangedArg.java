package gplx.objects.lists;

public class ItemsChangedArg<T> {
    public ItemsChangedArg(ItemsChangedType type, GfoListBase<T> itms) {
        this.type = type;
        this.itms = itms;
    }
    public ItemsChangedType Type() {return type;} private final ItemsChangedType type;
    public GfoListBase<T> Itms() {return itms;} private final GfoListBase<T> itms;
    public T Itm() {return itms.GetAt(0);}
}

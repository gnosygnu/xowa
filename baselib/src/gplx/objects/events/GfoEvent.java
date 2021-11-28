package gplx.objects.events;

import java.util.ArrayList;
import java.util.List;

public class GfoEvent<A> {
    private final List<GfoHandler<A>> handlers = new ArrayList<>();
    private final String name;
    private final GfoEventOwner owner;
    public GfoEvent(String name, GfoEventOwner owner) {
        this.name = name;
        this.owner = owner;
    }
    public void Add(GfoHandler<A> handler) {
        owner.EventsEnabledSet(true);
        handlers.add(handler);
    }
    public void Run(A args) {
        for (GfoHandler<A> handler : handlers) {
            handler.Run(owner, args);
        }
    }
}

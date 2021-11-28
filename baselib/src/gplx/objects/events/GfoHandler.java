package gplx.objects.events;

public interface GfoHandler<A> {
    void Run(GfoEventOwner sender, A args);
}

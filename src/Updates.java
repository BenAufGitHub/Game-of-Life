/**
 * in GameOfLife, the Updates are the only from a cell can evolve.
 * it can either be set alive, be set not alive,
 * it can be tracked/added (new) or untracked/deleted (Delete).
 */
public enum Updates {
    LIVE, DIE, NEW, DELETE
}
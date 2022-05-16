package game.kuprianowicz.michal;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Model
{
    public static final String BOMBS="";

    private SwingPropertyChangeSupport pcSupport =
            new SwingPropertyChangeSupport(this);
    private int bombs;
    private int level;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcSupport.removePropertyChangeListener(listener);
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        int oldValue = this.bombs;
        int newValue = bombs;

        this.bombs = bombs;
        pcSupport.firePropertyChange(BOMBS, oldValue, newValue);
    }


}


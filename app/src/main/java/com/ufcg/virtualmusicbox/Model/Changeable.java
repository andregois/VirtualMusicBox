package com.ufcg.virtualmusicbox.Model;

import java.util.List;

/**
 * Created by Victor on 5/9/2016.
 */
public interface Changeable {
    public void add(int position, Music item);

    public void remove(Music item);

    public void removeAll();

    public int getItemCount();

    public List<Music> getmDataset();
}

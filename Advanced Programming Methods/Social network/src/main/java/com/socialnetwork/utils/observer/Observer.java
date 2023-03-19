package com.socialnetwork.utils.observer;

import com.socialnetwork.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}

package component;

import model.FilterDrawerItem;

/**
 * Created by rutvik on 11/09/2016 at 8:36 PM.
 */

public class FilterDrawerComponent<T> implements FilterDrawerItem
{
    public static final int CHECK_BOX = 0;

    private final T model;
    private final int ViewType;

    public FilterDrawerComponent(final T model, final int viewType)
    {
        this.model = model;
        this.ViewType = viewType;
    }

    public T getModel()
    {
        return model;
    }

    public int getViewType()
    {
        return ViewType;
    }

}

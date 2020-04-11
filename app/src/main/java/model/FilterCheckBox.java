package model;

/**
 * Created by rutvik on 11/09/2016 at 8:52 PM.
 */

public class FilterCheckBox
{

    private final String label, value;

    private boolean isChecked = true;

    public FilterCheckBox(final String label, final String value)
    {
        this.label = label;
        this.value = value;
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    public void setChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public String getLabel()
    {
        return label;
    }

    public String getValue()
    {
        return value;
    }
}

package com.codebytes.DisplayItems;

import java.util.ArrayList;

import com.codebytes.base.Item;

public interface DisplayItems {
	ArrayList<Item> getTopN(int n);
	void refreshItems();
}

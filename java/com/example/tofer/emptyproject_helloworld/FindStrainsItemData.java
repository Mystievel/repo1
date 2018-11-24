package com.example.tofer.emptyproject_helloworld;

import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FindStrainsItemData extends FindStrainsActivity{
	private String effect;
	private int checkedBtnType;

	public FindStrainsItemData(String effect){
		this.effect = effect;
		this.checkedBtnType = -1;
	}

	// getters & setters
	public String getEffect() {
		return effect;
	}

	public int getFilter() {
		return checkedBtnType;
	}

	public void setFilter(int checkedBtn) {
		if (checkedBtn == IGNORE) {
			this.checkedBtnType = IGNORE;
		} else if (checkedBtn == MIN) {
			this.checkedBtnType = MIN;

		} else if (checkedBtn == MAX) {
			this.checkedBtnType = MAX;

		} else {
			checkedBtnType = -1;
		}
	}
}

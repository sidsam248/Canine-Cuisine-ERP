package com.cc.corpapp.model.property;

import javafx.util.StringConverter;

public class AgeTypeStringConverter extends StringConverter<AGE_TYPE>{

	@Override
	public AGE_TYPE fromString(String arg0) {
		
		return AGE_TYPE.valueOf(arg0);
	}

	@Override
	public String toString(AGE_TYPE arg0) {
		
		return arg0.toString();
	}

}

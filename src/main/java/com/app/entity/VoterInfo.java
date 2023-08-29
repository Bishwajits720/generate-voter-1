package com.app.entity;

import java.math.BigInteger;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoterInfo {
	
	private String voterNumber;
	private String name;
	private String nameOdia;
	private String nameFather;
	private String nameOdiaFather;
	private String address;
	private String addressOdia;
	private LocalDate dateOfBirth;
	private LocalDate voterDate;
	private String ConstituencyNoName;
	private String ConstituencyNoNameOdia;
	private String gender;
	

}

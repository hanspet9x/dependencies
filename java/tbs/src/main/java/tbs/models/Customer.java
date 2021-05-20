package tbs.models;

import java.io.Serializable;

public class Customer implements Serializable {
    //,":"a","other_phone2":"1","additional_info":"a"}


	private int id;
	private int seen = 0;
	private String agency;

	private String isParent;
	private String owner;
	private String companyIndividual;
	private String title;
	private String custno;
	private String firstname;
	private String lastname;
	private String othername;
	private String custname;
	private String serviceArea;
	private String locationDensity;
	private String zone;
	private String cc;
	private String houseNo;
	private String address1;
	private String city;
	private String state;
	private String country;
	private String customerType;
	private String businessType;
	private String telephone;
	private String email;
	private String telephone2;
	private String smsNotification;
	private String emailBill;
	private double lastPayment;
	private String lastPaymentDate;
	private String openingDate;
	private double balance;
	private String shipToHouseNo;
	private String shipToAddress1;
	private String shipToCity;
	private String shipToState;
	private String shipToCountry;
	private byte[] propertyPhoto;
	private byte[] ownerPhoto;
	private byte[] occupantPhoto;
	private String latitude;
	private String longitude;
	private String altitude;
	private String metered;
	private double lastMeterReading;
	private String prepaid;
	private double lastRecharged;
	private String status;
	private String statusLastReview;
	private String dateDisconnected;
	private String disconnectedBy;
	private String stopbill;
	private String active;
	private String disconnectionReason;
	private String chargeWater;
	private String chargeSewerage;
	private String chargeBorehole;
	private String waterRateCode1;
	private String waterRateCode2;
	private String waterRateCode3;
	private double consumptionEstimate1;
	private double consumptionEstimate2;
	private double consumptionEstimate3;
	private String baseWaterCharge;
	private String waterUsageCharge;
	private String payTax;
	private String applyServiceCharges;
	private String billFrequency;
	private String billMonth;
	private String meter1Sno;
	private String meter1Installed;
	private String meter1MiuNo;
	private String meter1Location;
	private int meter1MultiplierCode;
	private double meter1Multiplier;
	private String meter1Size;
	private String waterUnits1;
	private String waterSource1;
	private String meter2Sno;
	private String meter2Installed;
	private String meter2MiuNo;
	private String meter2Location;
	private int meter2MultiplierCode;
	private double meter2Multiplier;
	private String meter2Size;
	private String waterUnits2;
	private String waterSource2;
	private String oldCustno;
	private String landlordName;
	private String landlordPhone;
	private String contact1;
	private String contact2;
	private String contact1Phone;
	private String contact2Phone;
	private String notes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeen() {
		return seen;
	}

	public void setSeen(int seen) {
		this.seen = seen;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCompanyIndividual() {
		return companyIndividual;
	}

	public void setCompanyIndividual(String companyIndividual) {
		this.companyIndividual = companyIndividual;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getOthername() {
		return othername;
	}

	public void setOthername(String othername) {
		this.othername = othername;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public String getLocationDensity() {
		return locationDensity;
	}

	public void setLocationDensity(String locationDensity) {
		this.locationDensity = locationDensity;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getSmsNotification() {
		return smsNotification;
	}

	public void setSmsNotification(String smsNotification) {
		this.smsNotification = smsNotification;
	}

	public String getEmailBill() {
		return emailBill;
	}

	public void setEmailBill(String emailBill) {
		this.emailBill = emailBill;
	}

	public double getLastPayment() {
		return lastPayment;
	}

	public void setLastPayment(double lastPayment) {
		this.lastPayment = lastPayment;
	}

	public String getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(String lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getShipToHouseNo() {
		return shipToHouseNo;
	}

	public void setShipToHouseNo(String shipToHouseNo) {
		this.shipToHouseNo = shipToHouseNo;
	}

	public String getShipToAddress1() {
		return shipToAddress1;
	}

	public void setShipToAddress1(String shipToAddress1) {
		this.shipToAddress1 = shipToAddress1;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToState() {
		return shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShipToCountry() {
		return shipToCountry;
	}

	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}

	public byte[] getPropertyPhoto() {
		return propertyPhoto;
	}

	public void setPropertyPhoto(byte[] propertyPhoto) {
		this.propertyPhoto = propertyPhoto;
	}

	public byte[] getOwnerPhoto() {
		return ownerPhoto;
	}

	public void setOwnerPhoto(byte[] ownerPhoto) {
		this.ownerPhoto = ownerPhoto;
	}

	public byte[] getOccupantPhoto() {
		return occupantPhoto;
	}

	public void setOccupantPhoto(byte[] occupantPhoto) {
		this.occupantPhoto = occupantPhoto;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getMetered() {
		return metered;
	}

	public void setMetered(String metered) {
		this.metered = metered;
	}

	public double getLastMeterReading() {
		return lastMeterReading;
	}

	public void setLastMeterReading(double lastMeterReading) {
		this.lastMeterReading = lastMeterReading;
	}

	public String getPrepaid() {
		return prepaid;
	}

	public void setPrepaid(String prepaid) {
		this.prepaid = prepaid;
	}

	public double getLastRecharged() {
		return lastRecharged;
	}

	public void setLastRecharged(double lastRecharged) {
		this.lastRecharged = lastRecharged;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusLastReview() {
		return statusLastReview;
	}

	public void setStatusLastReview(String statusLastReview) {
		this.statusLastReview = statusLastReview;
	}

	public String getDateDisconnected() {
		return dateDisconnected;
	}

	public void setDateDisconnected(String dateDisconnected) {
		this.dateDisconnected = dateDisconnected;
	}

	public String getDisconnectedBy() {
		return disconnectedBy;
	}

	public void setDisconnectedBy(String disconnectedBy) {
		this.disconnectedBy = disconnectedBy;
	}

	public String getStopbill() {
		return stopbill;
	}

	public void setStopbill(String stopbill) {
		this.stopbill = stopbill;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDisconnectionReason() {
		return disconnectionReason;
	}

	public void setDisconnectionReason(String disconnectionReason) {
		this.disconnectionReason = disconnectionReason;
	}

	public String getChargeWater() {
		return chargeWater;
	}

	public void setChargeWater(String chargeWater) {
		this.chargeWater = chargeWater;
	}

	public String getChargeSewerage() {
		return chargeSewerage;
	}

	public void setChargeSewerage(String chargeSewerage) {
		this.chargeSewerage = chargeSewerage;
	}

	public String getChargeBorehole() {
		return chargeBorehole;
	}

	public void setChargeBorehole(String chargeBorehole) {
		this.chargeBorehole = chargeBorehole;
	}

	public String getWaterRateCode1() {
		return waterRateCode1;
	}

	public void setWaterRateCode1(String waterRateCode1) {
		this.waterRateCode1 = waterRateCode1;
	}

	public String getWaterRateCode2() {
		return waterRateCode2;
	}

	public void setWaterRateCode2(String waterRateCode2) {
		this.waterRateCode2 = waterRateCode2;
	}

	public String getWaterRateCode3() {
		return waterRateCode3;
	}

	public void setWaterRateCode3(String waterRateCode3) {
		this.waterRateCode3 = waterRateCode3;
	}

	public double getConsumptionEstimate1() {
		return consumptionEstimate1;
	}

	public void setConsumptionEstimate1(double consumptionEstimate1) {
		this.consumptionEstimate1 = consumptionEstimate1;
	}

	public double getConsumptionEstimate2() {
		return consumptionEstimate2;
	}

	public void setConsumptionEstimate2(double consumptionEstimate2) {
		this.consumptionEstimate2 = consumptionEstimate2;
	}

	public double getConsumptionEstimate3() {
		return consumptionEstimate3;
	}

	public void setConsumptionEstimate3(double consumptionEstimate3) {
		this.consumptionEstimate3 = consumptionEstimate3;
	}

	public String getBaseWaterCharge() {
		return baseWaterCharge;
	}

	public void setBaseWaterCharge(String baseWaterCharge) {
		this.baseWaterCharge = baseWaterCharge;
	}

	public String getWaterUsageCharge() {
		return waterUsageCharge;
	}

	public void setWaterUsageCharge(String waterUsageCharge) {
		this.waterUsageCharge = waterUsageCharge;
	}

	public String getPayTax() {
		return payTax;
	}

	public void setPayTax(String payTax) {
		this.payTax = payTax;
	}

	public String getApplyServiceCharges() {
		return applyServiceCharges;
	}

	public void setApplyServiceCharges(String applyServiceCharges) {
		this.applyServiceCharges = applyServiceCharges;
	}

	public String getBillFrequency() {
		return billFrequency;
	}

	public void setBillFrequency(String billFrequency) {
		this.billFrequency = billFrequency;
	}

	public String getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}

	public String getMeter1Sno() {
		return meter1Sno;
	}

	public void setMeter1Sno(String meter1Sno) {
		this.meter1Sno = meter1Sno;
	}

	public String getMeter1Installed() {
		return meter1Installed;
	}

	public void setMeter1Installed(String meter1Installed) {
		this.meter1Installed = meter1Installed;
	}

	public String getMeter1MiuNo() {
		return meter1MiuNo;
	}

	public void setMeter1MiuNo(String meter1MiuNo) {
		this.meter1MiuNo = meter1MiuNo;
	}

	public String getMeter1Location() {
		return meter1Location;
	}

	public void setMeter1Location(String meter1Location) {
		this.meter1Location = meter1Location;
	}

	public int getMeter1MultiplierCode() {
		return meter1MultiplierCode;
	}

	public void setMeter1MultiplierCode(int meter1MultiplierCode) {
		this.meter1MultiplierCode = meter1MultiplierCode;
	}

	public double getMeter1Multiplier() {
		return meter1Multiplier;
	}

	public void setMeter1Multiplier(double meter1Multiplier) {
		this.meter1Multiplier = meter1Multiplier;
	}

	public String getMeter1Size() {
		return meter1Size;
	}

	public void setMeter1Size(String meter1Size) {
		this.meter1Size = meter1Size;
	}

	public String getWaterUnits1() {
		return waterUnits1;
	}

	public void setWaterUnits1(String waterUnits1) {
		this.waterUnits1 = waterUnits1;
	}

	public String getWaterSource1() {
		return waterSource1;
	}

	public void setWaterSource1(String waterSource1) {
		this.waterSource1 = waterSource1;
	}

	public String getMeter2Sno() {
		return meter2Sno;
	}

	public void setMeter2Sno(String meter2Sno) {
		this.meter2Sno = meter2Sno;
	}

	public String getMeter2Installed() {
		return meter2Installed;
	}

	public void setMeter2Installed(String meter2Installed) {
		this.meter2Installed = meter2Installed;
	}

	public String getMeter2MiuNo() {
		return meter2MiuNo;
	}

	public void setMeter2MiuNo(String meter2MiuNo) {
		this.meter2MiuNo = meter2MiuNo;
	}

	public String getMeter2Location() {
		return meter2Location;
	}

	public void setMeter2Location(String meter2Location) {
		this.meter2Location = meter2Location;
	}

	public int getMeter2MultiplierCode() {
		return meter2MultiplierCode;
	}

	public void setMeter2MultiplierCode(int meter2MultiplierCode) {
		this.meter2MultiplierCode = meter2MultiplierCode;
	}

	public double getMeter2Multiplier() {
		return meter2Multiplier;
	}

	public void setMeter2Multiplier(double meter2Multiplier) {
		this.meter2Multiplier = meter2Multiplier;
	}

	public String getMeter2Size() {
		return meter2Size;
	}

	public void setMeter2Size(String meter2Size) {
		this.meter2Size = meter2Size;
	}

	public String getWaterUnits2() {
		return waterUnits2;
	}

	public void setWaterUnits2(String waterUnits2) {
		this.waterUnits2 = waterUnits2;
	}

	public String getWaterSource2() {
		return waterSource2;
	}

	public void setWaterSource2(String waterSource2) {
		this.waterSource2 = waterSource2;
	}

	public String getOldCustno() {
		return oldCustno;
	}

	public void setOldCustno(String oldCustno) {
		this.oldCustno = oldCustno;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordPhone() {
		return landlordPhone;
	}

	public void setLandlordPhone(String landlordPhone) {
		this.landlordPhone = landlordPhone;
	}

	public String getContact1() {
		return contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}

	public String getContact2() {
		return contact2;
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}

	public String getContact1Phone() {
		return contact1Phone;
	}

	public void setContact1Phone(String contact1Phone) {
		this.contact1Phone = contact1Phone;
	}

	public String getContact2Phone() {
		return contact2Phone;
	}

	public void setContact2Phone(String contact2Phone) {
		this.contact2Phone = contact2Phone;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
}

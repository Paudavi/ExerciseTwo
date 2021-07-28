#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@BodyWSRs
Feature: WSR builder application 
  I want to use this template for my feature file
  
  Background: Entering Salesforce with valid user
  Given initilize browser with chrome and going to Salesforce
  When user enters with username and password
	Then enters to WSRs application and Body WSRs
	And clicks on New
	
	@Story03_Test1
	Scenario: Checking if the required fields are present under their correspondent titles in the new WSRs body Check that the fields only accept the correct input.
	Then checks the title and subtitles
	And fills all the fields with valid data, save and edit
	Then check the hours fields with different values
	
	@Story03_Test2
	Scenario: Checking if the fields accept valid input. Checking if obligatory fields cannot be saved empyt.
	Then fills all the fields with valid data, save and edit
	And for each obligatory field, leave empty and try to save

	@Story04_Test1
	Scenario: Creating a new WSRs body checking that it only accepts the correct and necessary input
	Then fills all the fields with valid data, save and edit
	And checking Stories Information fields with cero and negative values
	
	@Story04_Test2
	Scenario: Creating an new WSRs body and checking that it only accepts valid input
	Then fills all the fields with valid data, save and edit
	And checking the dates fields
	
	@Story05_Test1
	Scenario: Creating a new WSRs body with valid input and sending it to the Manager when the information required is present.
	Then fills all the fields with valid data, save and edit
	And clear the user box, save and send to Manager, close error message
	Then edit user and send it again, check success message
 	And click once again to send to manager, expect error

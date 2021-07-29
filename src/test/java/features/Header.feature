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
@Header
Feature: WSR builder application
  I want to use this template for my feature file

  Background: Entering Salesforce with valid user
    Given initilize browser with chrome and going to Salesforce
    When user enters with username and password
    Then enters to WSRs application and Header
    And clicks on New and waits for the "New Header"

  @Story01_Test1
  Scenario: Checking if the required fields are present under their correspondent titles in the New Header window. Assuring that the input fields are according to the expected.
    And assert the titles and subtitles in the New Header
    Then filling with data the new header
    And saving and checking if the information is present
    Then edit the new header, and check the email input
    And clear all the obligatorily fields and save. Then leave every field empty and save.


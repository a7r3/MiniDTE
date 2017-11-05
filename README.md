<h1 align="center">DTE Admissions Portal</h1>

<h3 align="center">v0.2 | Beta</h3>

## Introduction

In this digital age, it has become the norm that digitization and automation of systems everywhere will result in more efficient and precise work. In this case, the process of admission of students has been greatly simplified using an online database system. Gone are the days where students used to wait in serpentine queues to await their admission status to colleges of their choice. Our objective in this project is to design a student&#39;s admission portal where students would be allotted their respective colleges according to their marks, their rank in the qualifying exam, and other important factors like choice of branch, address etc.

## Problem Definition

The problem to be implemented is here is to achieve the successful design of a complete student admission portal, based on the Department of Technical Education (DTE) website to allot students seats of their choice, based on the Common Admission Process (CAP) rounds.

## Modules

1. Login module

2. Form filling interface

a) Personal details

b) Academic details

c) College listing

d) Uploading of documents

3. Form summary module

## Implementation

The technologies implemented for this Project are:-

- JavaFX (GUI / Front-end &amp; Backend)
- MariaDB (MySQL Database)
- [JFoenix](https://github.com/jfoenixadmin/jfoenix) (Material Design Library for JavaFX / Front-end)

The **Login module** is the first stage of the Application, in which the user has to provide his/her credentials to it. Authentication is done by communicating with the SQL Server. Once user is authenticated, he/she is redirected to the Student Console

The **Student Console** consists of a set of **Sub Forms** that the student has to fill. When a user does not provide any input to a specified prompt, the submission won&#39;t be accepted. Such prompts are highlighted, so that the Student could find the incorrect prompts and get it filled easily.

The **College Listing** sub form consists of two Lists. One consisting of **the Available colleges** and the other consisting of the Colleges the Student has selected. User has add colleges from _&quot;Available Colleges List&quot;_ to the _&quot;Selected Colleges&quot;_ list by pressing the &quot; **&gt;&gt;**&quot; button. Selected colleges can be removed by selecting the college and pressing the &quot; **&lt;&lt;**&quot; Button

**Successful Submission of a form involves submitting provided User Data to the SQL Database**

The **Form Summary module** acts as a Map to the **Student Console**. It reports the forms which are filled and the forms which are not filled and is the Final Stage of Form filling.

Once the user fills all the forms, he has to approach this module, and submit the Form by pressing the &quot;Submit Form&quot; button.

## Results

### Student Console

![Login](https://raw.githubusercontent.com/a7r3/MiniDTE/master/imgs/login.png)
<br>
![Form Summary](https://raw.githubusercontent.com/a7r3/MiniDTE/master/imgs/form_summary.png)
<br>
![Personal Details Subform](https://raw.githubusercontent.com/a7r3/MiniDTE/master/imgs/personal_details.png)
<br>
![Academic Details Subform](https://raw.githubusercontent.com/a7r3/MiniDTE/master/imgs/academic_details.png)
<br>
![College Listing Subform](https://raw.githubusercontent.com/a7r3/MiniDTE/master/imgs/college_list.png)
<br>
![Upload Docs Subform](https://raw.githubusercontent.com/a7r3/MiniDTE/master/imgs/upload_docs.png)
<br>
![Navigation Drawer](https://raw.githubusercontent.com/a7r3/MiniDTE/master/imgs/nav_pane.png)
<br>
### Admin Console
![Login](https://raw.githubusercontent.com/a7r3/MiniDTE/master/imgs/admin_console.png)
<br>

## Conclusion

**Thus we have successfully implemented a form filling application which provides an interactive user interface for a student admission portal.**

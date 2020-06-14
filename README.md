# Example project - Prototype Asset Management
This project is created for a job application and it represents my skills in full-stack web development. 
  - The frontend is implemented with Angular and TypeScript
  - The Backend is implemented with Java Spring framework and Hibernate
    
## 01 Login:
  - Login with a pre-defined email and password account.
  - Input field validations:
  - No empty fields to be submitted to the server
  - No Cyrillic alphabet allowed
  - At least 6 symbols
  - Valid mail validation - mail format only.
  - Remember me option, which keeps the user session logged in forever until logout has been pressed.
  - Reset password link:
    - Opens a popup with mail input field. 
    - Upon entering a valid e-mail the system should reset the user password with a randomly generated 8 symbol combination and sent to the user via email.
  
## 02 Dashboard:
  - Main menu:
    - Show inside Projects button the number of "new projects" 
    - Show inside Products button the number of "in progress" 
    - List logged user name on the right + logout button. Logging out should be confirmed with a popup window to confirm the action.
  - General window:
    - Projects & Products widget:
    - Show the Projects’ number of "new projects" / “products in progress ”
    - Show "finished" projects number below
    - Add project / add product shortcuts
  - Activity:
    - List logged user activities such as "login”s, "new project", "new product" events.


## 03 Search:
  - Search field should search, find and list all results that contain the at least 3 letters typed in and contained in   -     - Project name, Company, Project manager, Product name, Serial number.
  - Indexing should be done in order speed up the search.
  - Clicking on Project name, Company, Project Manager should lead to a single Project page in opened/edit view.
  - Clicking on Product name or Serial number should lead to a single Product page in opened/edit view.

## 04 Projects:
  - The table should be able to be sorted by date created and quantity of attached products.
  - The table should be able to be filtered with from-to dates and statuses. The filtering is not automatic and should be done by pressing the Apply button. Clear filters button resets all filters.
  - Results per page dropdown should limit or expand the shown results by 5, 20 and 50
  - Products column consists of two buttons:
  - One contains the number of attached products with a link that sends the user to the Products page filtered by the corresponding project.
  - The second is a plus shortcut which when pressed sends the user to a window to add Products with a project name already inserted in the dropdown field.
  - Options link opens to view/edit the Project.
  -Pagination consists of listing the pages containing the results, previous and next page. The page results are calculated by the results shown. If the total results are 10 and the Results per page is set to 5, there should be two pages of results.

## 05 New project:
  - All fields are mandatory
  - Only options are “Cancel & close” and “Add & save”

## 06 View / edit project:
  - All fields can be edited
  - Attached products is a list with products that are attached to this project with some of the most important fields.
  - View details button should lead to the Products page with the previously selected project filtered.

## 07 Products:
  - The table should be able to be sorted by date created and quantity of products to be created.
  - The table should be able to be filtered with from-to date, statuses and project name. The filtering is not automatic and should be done by pressing the Apply button. Clear filters button resets all filters.
  - Results per page dropdown should limit or expand the shown results by 5, 20 and 50
  - Picture column contains the total number of uploaded images and shows the one selected as a thumb.
  - Options link opens to view/edit the Product.
  - Pagination is the same.
  - When a thumbnail of a product image is clicked, the image should open in a preview window with previous and next buttons of all images as a gallery.

## 08 New product:
  - All fields are mandatory
  - At least one image should be uploaded in the picture section. Limitation are: Image formats: png, jpg, gif. Size limit 12mb. Maximum of 10 images.
  - When the thumbnail is clicked, the image should open in a preview window with previous and next buttons as a gallery.
  - Images can be downloaded and deleted. When deleting an image, the user must confirm with a pop-up.
  - Selected image as a thumb with a radio button should appear as the thumb in the main product menu.
  - Options are “Cancel & close” and “Add & save”

## 09 Profile:
  - The user should be able to change his/her password by typing in the current password and twice the new password. Fields should validate that the new and the repeated are equal and are at least 8 chars.
  
## 10 Cookies:
  - A popup warning that alerts the visitors that the website uses cookies and to continue they should accept them.
  - When clicking accept, a cookie must be set that the user has accepted cookies. The window should not be visible to this particular user, not using cookies, but by entering the precise date and time of when the user has accepted in a separate table.

## General page options:
  - Every page has breadcrumbs which show the current location as the example Prototypes > Projects
  - Where applicable, for example adding a picture, changing status or deleting an object, a notification on the top right corner should appear stating the success of the operation. The notification should stay and auto disappear after 4 seconds.     - It should also be able to be cleared/closed manually.

## General table options:
  - The table has select all checkbox and individual select boxes. Select all should select and deselect all.
  - When at least one row is selected, an action option toolbar should appear, containing a dropdown with possible actions:     - Change status to: /all statuses/ and Delete action.

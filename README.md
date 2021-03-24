# Marks

Store marks and information for a course (e.g.: University). A `Course` contains many `Year`s which each contain many `Module`s which each contain many `Assessment`s.

# Usage

Run `gradle --console plain run -q` to be asked for course information on the command line and then a filename. It will create an excel file with that name containing all the course information and formulas linking together. At the moment there is not formatting and many cells needed only for calculation are shown.

# Features

The primary purpose of this program is to use course details to create an Excel spreadsheet with formulas. The spreadsheet will include spaces for the user to fill in marks as they are known and the formulas will update the weighted marks for the module, year and whole course so the user can see where they are at.

## Possible Future Features

- Ability to edit the configuration without starting again
- Export spreadsheet to webpage with better formatting
- Store marks themselves in program too
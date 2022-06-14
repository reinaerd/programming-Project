# Stratego web project group 23

## Parent group
https://git.ti.howest.be/TI/2020-2021/s2/programming-project/projects/students/group-23

## Remote urls 

* https://project-i.ti.howest.be/stratego-23/
* https://project-i.ti.howest.be/stratego-23/api/versions/original
* https://project-i.ti.howest.be/stratego-api-spec/

## Before you start 
* Search for the string XX and replace it with the group number.

## Please complete the following before committing the final version on the project
Please **add** any **instructions** required to 
* Make your application work if applicable 
* Be able to test the application (login data)
* View the wireframes

Also clarify
* If there are known **bugs**
  - If you click too fast, you'll sometimes get an error message in the 'message scroll', this doesn't break anything tho.
  - If you click on the Original, Duel or Infiltrator button on the home menu and it doesn't respond: Clear your local storage
    and refresh. The statistic page's storage can be a bit iffy if there are old objects in there. This shouldn't be the case 
    for you however. More of an issue we ran into with out-dated code.
  
* If you haven't managed to finish certain required functionality
  - Infiltrator can only infiltrate whilst in enemy territory.
  - Did not implement all of the priority 3 epics.

## Instructions for local CI testing
You can **run** the validator and Sonar with CSS and JS rules **locally.** There is no need to push to the server to check if you are compliant with our rules. In the interest of sparing the server, please result to local testing as often as possible. 

If everyone will push to test, the remote will not last. 

Please consult the Sonar guide at [https://git.ti.howest.be/TI/2020-2021/s2/programming-project/documentation/stratego-documentation/-/tree/master/sonar-guide](https://git.ti.howest.be/TI/2020-2021/s2/programming-project/documentation/stratego-documentation/-/tree/master/sonar-guide)

## Client 
In order to help you along with planning, we've provided a client roadmap (https://git.ti.howest.be/TI/2020-2021/s2/programming-project/documentation/stratego-documentation/-/blob/master/client-roadmap.md)[https://git.ti.howest.be/TI/2020-2021/s2/programming-project/documentation/stratego-documentation/-/blob/master/client-roadmap.md] 

## File structure
All files should be places in the `src` directory. 

**Do not** change the file structure of the folders outside of that directory. Within, you may do as you please. 


## Default files

### CSS 
The `reset.css` has aleady been supplied, but it's up to you and your team to add the rest of the styles. Please feel free to split those up in multiple files. We'll handle efficient delivery for products in production in later semesters. 

### JavaScript
A demonstration for connecting with the API has already been set up. We urge you to separate your JS files as **atomically as possible**. Add folders as you please.  
 
## Extra tips for CSS Grid
In case you get stuck or confused 
https://learncssgrid.com/

And for your convenience, yet use with caution
https://grid.layoutit.com/ 

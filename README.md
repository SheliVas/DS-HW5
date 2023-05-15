# DS-HW5
A repo for Homework 05 in Data Structures course

---

Collaboration Workflow Guide for Sheli and Gal

1. Switch to Branches:
   - Sheli: Switch to the `sheli-branch` using the following command:
     ```
     git checkout sheli-branch
     ```
   - Gal: Switch to the `gal-branch` using the following command:
     ```
     git checkout gal-branch
     ```

2. Work on Separate Parts:
   - Sheli: Make changes to the code on your local `sheli-branch`.
   - Gal: Make changes to the code on your local `gal-branch`.

3. Commit Changes:
   - Sheli: Add and commit your changes on the `sheli-branch` using the following commands:
     ```
     git add .
     git commit -m "Your commit message"
     ```
   - Gal: Add and commit your changes on the `gal-branch` using the following commands:
     ```
     git add .
     git commit -m "Your commit message"
     ```

4. Pull and Merge Changes:
   - Sheli: Before pushing your changes, ensure your `sheli-branch` is up to date with the `main` branch:
     ```
     git fetch
     git merge origin/main
     ```
   - Gal: Before pushing your changes, ensure your `gal-branch` is up to date with the `main` branch:
     ```
     git fetch
     git merge origin/main
     ```

5. Push Changes:
   - Sheli: Push your changes from the `sheli-branch` to the remote repository using the following command:
     ```
     git push origin sheli-branch
     ```
   - Gal: Push your changes from the `gal-branch` to the remote repository using the following command:
     ```
     git push origin gal-branch
     ```

6. Create and Review Pull Request:
   - Sheli: Go to the repository on GitHub and create a pull request to merge your `sheli-branch` into the `main` branch. You can create the pull request by visiting the following link:
     ```
     https://github.com/SheliVas/DS-HW5/pulls
     ```
   - Gal: Review Sheli's pull request, make comments, or suggest changes if necessary.

7. Merge Pull Request:
   - Sheli: Address any comments or make the suggested changes.
   - Gal: Once you are satisfied with the changes, approve and merge the pull request.

8. Update Local `main` Branch:
   - Both Sheli and Gal should switch to the `main` branch and update it with the latest changes:
     ```
     git checkout main
     git fetch
     git merge origin/main
     ```

9. Repeat Steps 1-8:
   - Both Sheli and Gal can repeat steps 1-8 to continue working on separate parts of the code and merging their changes.

By following this workflow guide, you and Gal can work together without conflicts, ensuring a smooth collaboration process. Remember to communicate effectively and coordinate your work to avoid any conflicts during the merging process.

---

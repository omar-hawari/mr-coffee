# General Info
This app is build with the principles of MVVM applied, using Dagger Hilt for Dependency Injection and Retrofit for REST API calls and Room for Database.

# Folder Structure

- Common: Holds common classes which are used in the entire project.
- Data: Holds the remote data classes and repository implementations.
- DI: Contains dependecy injection modules.
- Domain: Contains the app's data clases, the repository interface, and UseCases that are reusable across the project.
- Presentation: Contains the UI section of the application, including themes, colors, UI, ViewModels, etc.

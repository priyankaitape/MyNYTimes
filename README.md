# MyNYTimes
NY Times Most Popular Articles

This is a simple Android app which uses NY Times Most Popular Articles API to show list of articles. We can read the detailed articles by clicking the list item. 

The data is fetched from the API using Retrofit library. Retrofit turns the HTTP API(ApiClient.java) to java interface(APIInterface.java). The response (TopNewsResponse.java and NewsItem.java) is displayed in the Recycler View (ItemListActivity.java).When the article is tapped it shows the detailed article in a webview (ItemDetailFragment).

Steps for Running the code:

1) Download the project from github
2) Open Android Studio 
3) Select the option "Open an existing Android Studio project"
4) Select the project 'MyNYTimes'
5) After project sync completes, connect android device or launch emulator.
6) Click Toolbar->Run App

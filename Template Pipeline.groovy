node {
    stage('Clean')
    {
        deleteDir()
    }

    stage('Checkout')
    {
        git branch: 'master', 
            url: 'https://github.com/Ashley-Gibson/SOLID'
    }

    stage('Install Dependencies')
    {
        bat 'npm install'
    }

	stage('Build')
    {
        bat '\"C:\\_Project Code\\NuGet\\nuget.exe\" restore \"SOLID\\SOLID.sln\"'
        bat '\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\Enterprise\\MSBuild\\Current\\Bin\\MSBuild.exe\" \"SOLID\\SOLID.sln\" /p:Configuration=Release /p:Platform=\"Any CPU\"'
    }

    stage('Test')
    {
        bat  '\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\Enterprise\\Common7\\IDE\\mstest.exe" /resultsfile:\"C:\\_Personal Code\\Testing Reports\\Results.trx\" /testcontainer:\"C:\\_Personal Code\\SOLID\\SOLID\\Tests\\bin\\Release\\Tests.dll\" /nologo'
    }

	stage('Archive')
    {
        archiveArtifacts '/'
    }
    
    stage('Approval')
    {
        input 'Deploy?'
    }

    stage('Deploy')
    {
        bat '\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\Enterprise\\MSBuild\\Current\\Bin\\MSBuild.exe\" \"Publish.publishproj\"'
    }
}
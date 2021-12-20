def FaceAlgo(P, E, k):
    projects = new Set
    for i in 1...k:
        project = choice(P)
        for elem in projects:
            if (elem == project):
                fail
        projects.add(project)

    for employee in E:
        numberOfProjects = 0
        for project in projects:
            if (project.Ep.contains(employee)):
                numberOfProjects++
        if (numberOfProjects > 1):
            fail

    success

    Complexitate: O(k^2 + E^2 * k)


Apache DBCP test
================

## Introduction

The Apache commons [DBCP](http://commons.apache.org/dbcp/) component provides Connection pooling for JDBC connections. This project is for learning how DBCP connection pooling works.

A simple Java class uses the DBCP API and we use [Onyem JTracer](http://www.onyem.com/) to trace the calls. 

Read [How does DBCP work?](http://www.onyem.com/blog/how-does-dbcp-work.html) for the analysis of DBCP. 


## Usage

### Quick

To simply view the results, download this project and open results/jtrace in Onyem reader. Download the reader [here](http://www.onyem.com/download.html) for your plaform.

### Configure

To run the program you need to provide the location of the Onyem agent. This needs to be specified in the pom.xml in the properties section as shown below.

    <!--  Configure Onyem JTracer agent path -->
    <properties>
        <agent-path>/opt/onyem-jtracer-linux-x64/agent/libjtracer-agent.so</agent-path>
    <properties>

For windows users, the path to the agent dll needs to be provided.

### Run

Run the project by invoking the maven command below.

    mvn clean install exec:exec
    
The output of the execution is stored in results/out


## Feedback

Please provide feedback on [Github](https://github.com/rrevo) or [Onyem](http://www.onyem.com/contact.html)

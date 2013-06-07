#!/usr/bin/env ruby

#### Create the Java distribution tar file

require 'ftools'

dir = 'BioC_Java_1.0'

Dir.mkdir dir unless File.exists? dir

files = %w{.classpath .gitignore .project BioC.dtd LICENSE.txt README.txt}
files.each { |f| File.copy f, dir }


lib = "#{dir}/lib"
Dir.mkdir lib
File.copy 'lib/bioc.jar', lib

scripts = "#{dir}/scripts"
Dir.mkdir scripts
Dir["scripts/*.sh"].each { |f| File.copy f, scripts }

output = "#{dir}/output"
Dir.mkdir output
File.copy 'output/everything-nolib.xml', output

dirs = %w{ bin src xml }
dirs.each { |d| system "cp -r #{d} #{dir}" }
  
system "tar cfz #{dir}.tar.gz #{dir}"

system "rm -r #{dir}"

$param1 = $args[0]  # Java class name
$param2 = $args[1]  # Data file index
$param3 = $args[2]  # Number of threads
$param4 = $args[3]  # Number of runs

# Execute Java class
$sum = 0
for ($i = 0; $i -lt $param4; $i++) {
    Write-Host "Run" ($i+1)
    $time = java $args[0] $args[1] $args[2]  # run Java class
    Write-Host $time
    $sum += $time
    Write-Host ""
}
$average = $sum / $i
Write-Host "Average time execution:" $average

# Create .csv file
if (!(Test-Path out.csv)){
    New-Item out.csv -ItemType File
    # Write data in .csv
    Set-Content out.csv 'Data file index,Number of threads,Execution time (ms)'
}

# Append
Add-Content out.csv "$($args[1]),$($args[2]),$($average)"

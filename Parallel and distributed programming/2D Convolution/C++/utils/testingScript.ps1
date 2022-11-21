$param1 = $args[0]  # .exe file name
$param2 = $args[1]  # Data file index
$param3 = $args[2]  # Number of threads
$param4 = $args[3]  # Number of runs

# Execute
$sum = 0
for ($i = 0; $i -lt $param4; $i++) {
    Write-Host "Run" ($i+1)
    $time = (cmd /c .\$param1 $param2 $param3 2`>`&1)
    Write-Host $time
    $sum += $time
    Write-Host ""
}
$average = $sum / $i
Write-Host "Average execution time:" $average

# Create .csv file
if (!(Test-Path outC.csv)) {
    New-Item outC.csv -ItemType File
    # Write data in .csv
    Set-Content outC.csv 'Data file index,Number of threads,Execution time (ms)'
}

# Append
Add-Content outC.csv "$($args[1]),$($args[2]),$($media)"

# Test Instructions 

# JZ

Instruction: 001010 01 00 1 00110 (10534)

Jump to an address if the contents of Register 1 is equal to 0. 

1. Set the instruction at index 10.
2. Set R[1] = 0.
3. Look at PC. It should be 0.

# JNE

Instruction: 001011 10 00 0 00110 (11782)
  
Jump to an address if the contents of Register 2 is not equal to 0.

1. Set the instruction at index 10. 
2. Set R[2] = 0. Set PC = 10.
4. Look at PC. It should be 11. 


# JCC

Instruction: 001100 01 00 0 11111 (12575)

Jump to an address if the condition code is set. 

1. Set the instruction at index 10. 
2. Set CC[1] = true
3. Set PC = 10. 
4. Look at PC. It should be 31. 


# JMA

Instruction: 001101 00 00 1 10000 (13360)

Jump to an address. 

1. Set the instruction at index 10.
2. Set the contents of address 16 to 37. Set PC = 10. 
3. Look at PC. It should be 37 + 10 = 47. 

# JSR

Instruction: 001110 00 00 0 10000 (14352)

Jump and save address at GPR[3].

1. Set the instruction at index 10.
2. Set the contents of address 16 to 49. Set PC = 10.
3. Look at PC. It should be 10 + 16 = 26.  
4. Look at R[3]. It should be 11. 

# RFS * Not working fully!

Return from subroutine with R[0] being the intermedate and PC being the contents of R[3].

Instruction: 001111 00 00 0 11000 (15384)

1. Set the instruction at index 10. 
2. Set PC = 10. 
3. Set GPR[3] = 1.
4. Look at GPR[0]. It should be 24. 
5. Look at PC. It should be 1.

# SOB

Instruction: 010000 01 00 0 11100 (16668)

Subtract 1 from register 1 and branch if the value is greater than 0. 

1. Set the instruction at index 10. 
2. Set PC = 10. 
3. Set GPR[1] = 13. 
5. Look at PC. It should be 28. 
6. Look at GPR[1]. It should be 12. 

# JGE

Instruction: 010001 00 00 0 00011 (17411)

1. Set the instruction at index 10. 
2. Set PC = 10. 
3. Set GPR[0] = 11. 
4. Look at PC. It should be 3. 

# MLT

Instruction: 010100 00 10 0 00000 (20608)

1. Set the instruction at index 10. 
2. Set PC = 10.
3. Set GPR[0] = 12. Set GPR[2] = 3. 
4. GPR[0], GPR[1] should be 36 and 36 respectively. 

# DVD

Instruction: 010101 00 10 0 00000 (21632)

1. Set the instruction at index 10. 
2. Set PC = 10.
3. Set GPR[0] = 12. Set GPR[2] = 3. 
4. GPR[0], GPR[1] should be 4 and 0. 

# TRR

Instruction: 010110 00 10 0 00000 (22656)

1. Set the instruction at index 10. 
2. Set PC = 10.
3. Set GPR[0] = 12. Set GPR[2] = 12. 
4. Check CC[3]. It should be true. 

# AND

Instruction: 010111 00 10 0 00000 (23680)

1. Set the instruction at index 10. 
2. Set PC = 10.
3. Set GPR[0] = 3. Set GPR[2] = 3. 
4. Check GPR[0]. It should be 3. 

# OR

Instruction: 11000 00 10 0 00000 (24704)

1. Set the instruction at index 10. 
2. Set PC = 10.
3. Set GPR[0] = 3. Set GPR[2] = 5. 
4. Check GPR[0]. It should be 7. 

# NOT

Instruction: 11001 00 10 0 00000 (25728)

1. Set the instruction at index 10. 
2. Set PC = 10.
3. Set GPR[0] = 3. 
4. Check GPR[0]. It should be 0. 

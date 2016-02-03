local internet = require("internet")
local component = require("component")

local rs = component.redstone
local running = true 

local state = true

SERVER_IP = '192.168.0.254'
 

while running do
	if(handle) then
		print("if handle")
		data = string.byte(handle:read(1))
		print(data)
		if (data == 1) then
			state = not state
			if (state) then
				rs.setOutput(2, 15)

			else
				rs.setOutput(2, 0)
			end
		elseif (data == 16) then
			handle:write(string.char(16))
		else
			handle:close()
			handle = nil
		end
	else
		print("if no handle")
		handle = internet.open(SERVER_IP, 9993)
		handle:setTimeout(1000)
	end
end
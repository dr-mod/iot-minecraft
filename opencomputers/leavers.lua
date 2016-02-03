local internet = require("internet")
local event = require("event")

local char_space = string.byte(" ")
local running = true 

SERVER_IP = '192.168.0.254'

function unknownEvent()
  -- do nothing if the event wasn't relevant
end
 
function signalSend(id, status)
	print("id: " .. id .. "; status: " .. status)
	handle = internet.open(SERVER_IP, 9992)
	handle:write(string.char(id))
	handle:write(string.char(status))
	handle:flush()
	handle:close()
end

function calculateLedState(side, signal)
	local led = (side == 4) and 1 or side
	local high = (signal == 0) and 1 or 0
	return led, high
end

local myEventHandlers = setmetatable({}, { __index = function() return unknownEvent end })

function myEventHandlers.redstone_changed(_, side, signal)
	signalSend(calculateLedState(side, signal))
end

function myEventHandlers.key_up(_, char, _, _)
  if (char == char_space) then
    running = false
  end
end
 

function handleEvent(eventID, ...)
  if (eventID) then 
    myEventHandlers[eventID](...) 
  end
end
 

while running do
  handleEvent(event.pull())
end